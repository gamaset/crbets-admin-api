package com.gamaset.crbetadmin.service;

import static com.gamaset.crbetadmin.infra.log.LogEvent.create;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.gamaset.crbetadmin.infra.configuration.security.UserPrinciple;
import com.gamaset.crbetadmin.infra.exception.BusinessException;
import com.gamaset.crbetadmin.infra.exception.NotFoundException;
import com.gamaset.crbetadmin.infra.log.LogEvent;
import com.gamaset.crbetadmin.infra.utils.UserProfileUtils;
import com.gamaset.crbetadmin.repository.AgentRepository;
import com.gamaset.crbetadmin.repository.WalletRepository;
import com.gamaset.crbetadmin.repository.entity.AgentModel;
import com.gamaset.crbetadmin.repository.entity.WalletModel;
import com.gamaset.crbetadmin.repository.entity.WalletStatusEnum;
import com.gamaset.crbetadmin.schema.request.ManagerRequest;

@Service
public class WalletService {
	
	private static final Logger LOG_ACTION = LogEvent.logger("ACTION");
	private static final Logger LOG_ERROR = LogEvent.logger("ERROR");

	private WalletRepository walletRepository;
	private AgentRepository agentRepository;

	@Autowired
	public WalletService(WalletRepository walletRepository, AgentRepository agentRepository) {
		this.walletRepository = walletRepository;
		this.agentRepository = agentRepository;
	}

	public List<WalletModel> list(WalletStatusEnum walletStatus) {
		
		UserPrinciple principle = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<WalletModel> wallets = new ArrayList<>();
		LOG_ACTION.info(create("Listando Carteiras").add("userId", principle.getId()).build());
		
		Optional<AgentModel> agentOpt = agentRepository.findByUserId(principle.getId());
		if(!agentOpt.isPresent()) {
			LOG_ERROR.error(create("Agente não encontrado").add("agentId", principle.getId()).build());
			throw new NotFoundException("Agente não encontrado");
		}
		
		if(Objects.isNull(walletStatus) && UserProfileUtils.isAdminOrManager(principle)) {
			wallets = (List<WalletModel>) walletRepository.findAll();
		}else if (!Objects.isNull(walletStatus)){
			wallets = walletRepository.findByAgentUserIdAndStatus(principle.getId(), walletStatus);
		}else {
			wallets = walletRepository.findByAgentUserId(principle.getId());
		}
		
		return wallets;
	}

//	@Transactional
//	public ManagerModel insert(ManagerRequest request) {
//		LOG_ACTION.info(create("Cadastrando Gerente").build());
//		ManagerModel response = null;
//		try {
//			validateInsert(request);
//			UserModel userCreated = authService.signUp(new SignUpRequest(request, "MANAGER"));
//			response = managerRepository.save(new ManagerModel(userCreated));
//		} catch (BusinessException e) {
//			LOG_ERROR.error(create("Erro ao criar Gerente").add(e).build());
//			throw e;
//		}
//
//		return response;
//	}

	private void validateInsert(ManagerRequest request) {
		try {
			requireNonNull(request.getEmail(), "Email não pode ser nulo");
			requireNonNull(request.getName(), "Nome não pode ser nulo");
			requireNonNull(request.getPassword(), "Senha não pode ser nulo");
			requireNonNull(request.getTaxId(), "CPF não pode ser nulo");
			requireNonNull(request.getUsername(), "Nome de Usuario não pode ser nulo");
		} catch (NullPointerException nEx) {
			throw new BusinessException(nEx.getMessage());
		}

	}
}
