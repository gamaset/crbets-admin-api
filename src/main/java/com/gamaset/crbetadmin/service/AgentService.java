package com.gamaset.crbetadmin.service;

import static com.gamaset.crbetadmin.infra.log.LogEvent.create;
import static com.gamaset.crbetadmin.infra.utils.CPFValidator.isValid;
import static com.gamaset.crbetadmin.infra.utils.UserProfileUtils.isAdmin;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.isTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gamaset.crbetadmin.infra.configuration.security.AuthService;
import com.gamaset.crbetadmin.infra.configuration.security.UserPrinciple;
import com.gamaset.crbetadmin.infra.exception.BusinessException;
import com.gamaset.crbetadmin.infra.exception.NotFoundException;
import com.gamaset.crbetadmin.infra.log.LogEvent;
import com.gamaset.crbetadmin.repository.AgentRepository;
import com.gamaset.crbetadmin.repository.ManagerRepository;
import com.gamaset.crbetadmin.repository.WalletBalanceRepository;
import com.gamaset.crbetadmin.repository.WalletRepository;
import com.gamaset.crbetadmin.repository.entity.AgentModel;
import com.gamaset.crbetadmin.repository.entity.ManagerModel;
import com.gamaset.crbetadmin.repository.entity.UserModel;
import com.gamaset.crbetadmin.repository.entity.WalletBalanceModel;
import com.gamaset.crbetadmin.repository.entity.WalletModel;
import com.gamaset.crbetadmin.repository.entity.WalletStatusEnum;
import com.gamaset.crbetadmin.schema.request.AgentRequest;
import com.gamaset.crbetadmin.schema.request.SignUpRequest;
import com.gamaset.crbetadmin.schema.response.AgentResponse;

@Service
public class AgentService {

	private static final Logger LOG_ACTION = LogEvent.logger("ACTION");
	private static final Logger LOG_ERROR = LogEvent.logger("ERROR");

	private AuthService authService;
	private ManagerRepository managerRepository;
	private AgentRepository agentRepository;
	private WalletRepository walletRepository;
	private WalletBalanceRepository walletBalanceRepository;

	@Autowired
	public AgentService(AuthService authService, ManagerRepository managerRepository, AgentRepository agentRepository,
			WalletRepository walletRepository, WalletBalanceRepository walletBalanceRepository) {
		this.authService = authService;
		this.managerRepository = managerRepository;
		this.agentRepository = agentRepository;
		this.walletRepository = walletRepository;
		this.walletBalanceRepository = walletBalanceRepository;
	}

	public List<AgentResponse> list() {

		List<AgentModel> response = null;

		UserPrinciple principle = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (isAdmin(principle)) {
			LOG_ACTION.info(create("Listando Agentes").add("user", principle.getId()).add("authority", "admin").build());
			response = (List<AgentModel>) agentRepository.findAll();
		} else {
			LOG_ACTION.info(create("Listando Agentes").add("user", principle.getId()).add("authority", "agent").build());
			response = agentRepository.findByManagerId(principle.getId());
		}

		return convertList(response);
	}

	@Transactional
	public AgentResponse insert(AgentRequest request) {
		// TODO: add validation component

		AgentModel agentCreated = null;
		
		try {
			LOG_ACTION.info(create("Iniciando validação de Agente").build());
			validationInsert(request);
			
			UserPrinciple principle = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			LOG_ACTION.info(create("Cadastrando Agente").add("user", principle.getId()).build());

			ManagerModel manager = managerRepository.findByUserId(principle.getId());
			if(Objects.isNull(manager)) {
				LOG_ERROR.error(create("Gerente não encontrado").add("user", principle.getId()).build());
				throw new NotFoundException("Gerente não encontrado");
			}
			UserModel userCreated = authService.signUp(new SignUpRequest(request, "AGENT"));

			AgentModel agentModel = new AgentModel(userCreated, manager.getUser());
			agentCreated = agentRepository.save(agentModel);

			LOG_ACTION.info(create("Cadastrando Carteira do Agente").add("user", principle.getId()).build());
			
			WalletModel walletCreated = walletRepository.save(new WalletModel(agentCreated));
			walletBalanceRepository.save(new WalletBalanceModel(walletCreated, request.getBudget(), request.getPercentComission()));
			
		} catch (BusinessException e) {
			LOG_ERROR.error(create("Erro ao criar o Agente").add(e).build());
			throw e;
		}

		return convert(agentCreated);
	}

	public void addBudget(Long agentId, BigDecimal amount) {

	}

//	public void subtractBudget(Long agentId, BigDecimal amount) {
//
//		UserPrinciple principle = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//		LOG_ACTION.info(create("Atualizando Budget do Agente").add("amount", amount).add("userId", principle.getId()).build());
//
//		Optional<AgentModel> agentOpt  = null;
//		
//		if(isAdmin(principle)) {
//			agentOpt = agentRepository.findById(agentId);
//		}else if(UserProfileUtils.isAdminOrManager(principle)){
//			agentOpt = agentRepository.findByIdAndManagerId(agentId, principle.getId());
//		}else { //AGENT
//			agentOpt = agentRepository.findById(agentId);
//		}
//		
//		if (agentOpt.isPresent()) {
//			AgentModel agentModel = agentOpt.get();
//			subtractAgentBudget(agentModel, amount);
//		} else {
//			LOG_ERROR.error(create("Agente não encontrado ").add("userId", principle.getId()).build());
//			throw new BusinessException(String.format("Agente não encontrado ID %s", principle.getId()));
//		}
//
//	}
//	
//	private void subtractAgentBudget(AgentModel agentModel, BigDecimal amount) {
//		if (agentModel.getBudget().compareTo(amount) >= 0) {
//			agentModel.setBudget(agentModel.getBudget().subtract(amount));
//			AgentModel agentUpdated = agentRepository.save(agentModel);
//			agentBudgetHistoryRepository.save(new AgentBudgetHistoryModel(agentUpdated, agentUpdated.getBudget()));
//		} else {
//			throw new BusinessException(String.format("Orçamento insuficiente - Atual [%s] Esperado [%s]",
//					agentModel.getBudget(), amount));
//		}
//	}
	
	private void validationInsert(AgentRequest request) {
		try {
			requireNonNull(request.getBudget(), "Orçamento não pode ser nulo");
			requireNonNull(request.getEmail(), "Email não pode ser nulo");
			requireNonNull(request.getName(), "Nome não pode ser nulo");
			requireNonNull(request.getPassword(), "Senha não pode ser nulo");
			requireNonNull(request.getTaxId(), "CPF não pode ser nulo");
			isTrue(isValid(request.getTaxId()), "CPF Inválido");
		}catch (NullPointerException | IllegalArgumentException e) {
			throw new BusinessException(e.getMessage());
		}
	}
	
	private List<AgentResponse> convertList(List<AgentModel> agents){
		List<AgentResponse> response = new ArrayList<>();
		
		if (!isNull(agents) && agents.size() > 0) {
			agents.stream().forEach(a -> {
				response.add(convert(a));
			});
		}
		
		return response;
	}
	
	private AgentResponse convert(AgentModel agent) {
		return new AgentResponse(agent, walletBalanceRepository.findByWalletAgentUserIdAndWalletStatus(agent.getUser().getId(), WalletStatusEnum.OPEN));
	}

}
