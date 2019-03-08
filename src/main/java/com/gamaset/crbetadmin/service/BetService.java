package com.gamaset.crbetadmin.service;

import static com.gamaset.crbetadmin.infra.log.LogEvent.create;
import static com.gamaset.crbetadmin.infra.utils.UserProfileUtils.isAdmin;
import static com.gamaset.crbetadmin.repository.entity.BetStatusEnum.CANCELLED;
import static com.gamaset.crbetadmin.repository.entity.BetStatusEnum.LOSE;
import static com.gamaset.crbetadmin.repository.entity.BetStatusEnum.PENDING;
import static com.gamaset.crbetadmin.repository.entity.BetStatusEnum.REGISTERING;
import static com.gamaset.crbetadmin.repository.entity.BetStatusEnum.WON;
import static com.gamaset.crbetadmin.repository.entity.BetStatusEnum.valueOf;
import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.isTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.gamaset.crbetadmin.infra.configuration.security.UserPrinciple;
import com.gamaset.crbetadmin.infra.exception.BusinessException;
import com.gamaset.crbetadmin.infra.exception.NotFoundException;
import com.gamaset.crbetadmin.infra.log.LogEvent;
import com.gamaset.crbetadmin.infra.utils.UserProfileUtils;
import com.gamaset.crbetadmin.repository.BetHistoryRepository;
import com.gamaset.crbetadmin.repository.BetRepository;
import com.gamaset.crbetadmin.repository.CompetitionRepository;
import com.gamaset.crbetadmin.repository.CustomerRepository;
import com.gamaset.crbetadmin.repository.EventRepository;
import com.gamaset.crbetadmin.repository.entity.BetHistoryModel;
import com.gamaset.crbetadmin.repository.entity.BetModel;
import com.gamaset.crbetadmin.repository.entity.BetStatusEnum;
import com.gamaset.crbetadmin.repository.entity.CustomerModel;
import com.gamaset.crbetadmin.repository.entity.EventModel;
import com.gamaset.crbetadmin.schema.request.BetRequest;
import com.gamaset.crbetadmin.schema.request.BetUpdateStatusRequest;
import com.gamaset.crbetadmin.schema.response.BetResponse;

@Service
public class BetService {

	private static final Logger LOG_ACTION = LogEvent.logger("ACTION");
	private static final Logger LOG_ERROR = LogEvent.logger("ERROR");

	@Value("${betwin.config.commissionPercent}")
	private BigDecimal commissionPercentConfig;

	private BetRepository betRepository;
	private BetHistoryRepository betHistoryRepository;
	private EventRepository eventRepository;
	private CustomerRepository customerRepository;
	private CompetitionRepository competitionRepository;
	private WalletBalanceService balanceService;

	@Autowired
	public BetService(BetRepository betRepository, BetHistoryRepository betHistoryRepository,
			EventRepository eventRepository, CustomerRepository cRepository, CompetitionRepository competitionRepository, 
			WalletBalanceService balanceHistoryService) {
		this.betRepository = betRepository;
		this.betHistoryRepository = betHistoryRepository;
		this.eventRepository = eventRepository;
		this.customerRepository = cRepository;
		this.competitionRepository = competitionRepository;
		this.balanceService = balanceHistoryService;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@Transactional
	public BetResponse createBet(BetRequest request) {

		try {

			validateInsert(request);

			LOG_ACTION.info(create("Criando Aposta").add("taxId", request.getTaxId()).add("betValue", request.getBetValue()).build());

			Optional<CustomerModel> customerOpt = customerRepository.findByUserTaxId(request.getTaxId());
			if (!customerOpt.isPresent()) {
				LOG_ERROR.error(create("Cliente não encontrado").add("cpf", request.getTaxId()).build());
				throw new NotFoundException("Cliente não encontrado");
			}

			BetModel bet = new BetModel(request, customerOpt.get(), commissionPercentConfig);

			BetModel betCreated = betRepository.save(bet);
			for (EventModel event : request.getEvents()) {
				event.setBet(betCreated);
				event.setCompetition(competitionRepository.findById(event.getCompetition().getId()).get());
				eventRepository.save(event);
			}

			betHistoryRepository.save(new BetHistoryModel(betCreated, betCreated.getStatus()));

			return new BetResponse(betCreated, request.getEvents());
		} catch (BusinessException e) {
			LOG_ERROR.error(create(e.getMessage()).build());
			throw e;
		}
	}

	/**
	 * Listagem de Apostas
	 * 
	 * @return
	 */
	public List<BetResponse> list() {

		UserPrinciple principle = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		List<BetResponse> response = new ArrayList<>();
		LOG_ACTION.info(create("Listando Apostas").add("userId", principle.getId())
				.add("roles", principle.getAuthorities()).build());

		List<BetModel> bets = null;
		if (isAdmin(principle)) {
			bets = (List<BetModel>) betRepository.findAll();
		} else if (UserProfileUtils.isAdminOrAgent(principle)) {
			bets = (List<BetModel>) betRepository.findByCustomerAgentId(principle.getId());
		} else {
			LOG_ERROR.error(create("Perfil de Usuario não pode acessar esses dados.").build());
			throw new BusinessException("Perfil de Usuario não pode acessar esses dados.");
		}

		if (!CollectionUtils.isEmpty(bets)) {
			bets.stream().forEach(b -> {
				response.add(new BetResponse(b, null));
			});
		}

		return response;
	}

	/**
	 * Atualiza Status da aposta
	 * 
	 * @param betId
	 * @param request
	 */
	@Transactional
	public void updateStatusBet(Long betId, BetUpdateStatusRequest request) {

		UserPrinciple principle = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		LOG_ACTION.info(
				create("Atualizando Status da Aposta").add("betId", betId).add("userId", principle.getId()).build());

		if (!UserProfileUtils.isAgent(principle)) {
			LOG_ERROR.error(create("Perfil de Usuario não pode acessar esses dados.").build());
			throw new BusinessException("Perfil de Usuario não pode acessar esses dados.");
		}

		Optional<BetModel> betOpt = betRepository.findByIdAndCustomerAgentId(betId, principle.getId());

		if (betOpt.isPresent()) {
			BetModel betEntity = betOpt.get();

			BetStatusEnum betStatusRequest = valueOf(request.getStatus()).get();

			if (!betStatusRequest.equals(betEntity.getStatus())) {
				executeFlowChangeStatus(betEntity.getStatus(), betStatusRequest, betEntity, principle.getId());
			}

		} else {
			LOG_ERROR.error(
					create("Aposta não encontrada").add("betId", betId).add("userId", principle.getId()).build());
			throw new BusinessException(String.format("Aposta não encontrada ID %s", betId));
		}
	}

	/**
	 * Busca detalhe da aposta por ID
	 * 
	 * @param betId
	 * @return
	 */
	public BetResponse getBet(Long betId) {

		UserPrinciple principle = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		LOG_ACTION.info(
				create("Buscando detalhe da Aposta").add("betId", betId).add("userId", principle.getId()).build());

		if (!UserProfileUtils.isAdminOrAgent(principle)) {
			LOG_ERROR.error(create("Perfil de Usuario não pode acessar esses dados.").build());
			throw new BusinessException("Perfil de Usuario não pode acessar esses dados.");
		}

		BetResponse response = null;
		Optional<BetModel> betOpt = null;
		if (UserProfileUtils.isAdmin(principle)) {
			betOpt = betRepository.findById(betId);
		} else {
			betOpt = betRepository.findByIdAndCustomerAgentId(betId, principle.getId());
		}

		if (betOpt.isPresent()) {
			response = new BetResponse(betOpt.get(), eventRepository.findByBetId(betId));
		} else {
			LOG_ERROR.error(create("Aposta não encontrada").add("betId", betId).add("userId", principle.getId()).build());
			throw new BusinessException(String.format("Aposta não encontrada ID %s", betId));
		}
		
		return response;
	}

	private void validateInsert(BetRequest request) {
		try {
			requireNonNull(request);
			requireNonNull(request.getTaxId(), "CPF não pode ser nulo");
			requireNonNull(request.getBetValue(), "Valor da aposta não pode ser nulo");
			requireNonNull(request.getEvents(), "Lista de Eventos não pode ser nulo");
			isTrue(request.getEvents().size() >= 2, "Lista de Eventos deve conter no minimo 2 eventos");
			requireNonNull(request.getEvents(), "Lista de Eventos não pode ser nulo");
		} catch (NullPointerException | IllegalArgumentException e) {
			throw new BusinessException(e);
		}

	}

	private void executeFlowChangeStatus(BetStatusEnum actual, BetStatusEnum newStatus, BetModel betEntity, Long agentUserId) {
		
		if(actual.equals(BetStatusEnum.LOSE) || actual.equals(BetStatusEnum.WON)) {
			LOG_ERROR.error(create("Não é possivel alterar o status da aposta, pois ela já foi encerrada").build());
			throw new BusinessException("Não é possivel alterar o status da aposta, pois ela já foi encerrada.");
		}
		
		if (actual.equals(REGISTERING) && newStatus.equals(PENDING)) {
			LOG_ERROR.error(create("Não é possivel alterar o status da aposta, pois ela já foi efetivada").build());
			throw new BusinessException("Não é possivel alterar o status da aposta, pois ela já foi efetivada.");
		}

		if (actual.equals(REGISTERING) && (newStatus.equals(LOSE) || newStatus.equals(CANCELLED))) {
			betEntity.setStatus(newStatus);
			BetModel betModel = betRepository.save(betEntity);
			betHistoryRepository.save(new BetHistoryModel(betModel, betModel.getStatus()));
			return;
		}
		
		if ((actual.equals(PENDING) && newStatus.equals(REGISTERING)) || (actual.equals(REGISTERING) && newStatus.equals(WON))) {
			betEntity.setStatus(newStatus);
			BetModel betModel = betRepository.save(betEntity);
			betHistoryRepository.save(new BetHistoryModel(betModel, betModel.getStatus()));
			balanceService.updateByBetStaus(agentUserId, newStatus, betModel);
		}
		
	}
}
