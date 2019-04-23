package com.gamaset.crbetadmin.service;

import static com.gamaset.crbetadmin.infra.log.LogEvent.create;
import static com.gamaset.crbetadmin.infra.utils.CPFValidator.isValid;
import static com.gamaset.crbetadmin.infra.utils.UserProfileUtils.isAdmin;
import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.isTrue;

import java.util.List;
import java.util.Optional;

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
import com.gamaset.crbetadmin.infra.utils.UserProfileUtils;
import com.gamaset.crbetadmin.repository.AgentRepository;
import com.gamaset.crbetadmin.repository.CustomerRepository;
import com.gamaset.crbetadmin.repository.entity.AgentModel;
import com.gamaset.crbetadmin.repository.entity.BetModel;
import com.gamaset.crbetadmin.repository.entity.CustomerModel;
import com.gamaset.crbetadmin.schema.request.CustomerRequest;

@Service
public class CustomerService {

	private static final Logger LOG_ACTION = LogEvent.logger("ACTION");
	private static final Logger LOG_ERROR = LogEvent.logger("ERROR");
	
	private AuthService authService;
	private AgentRepository agentRepository;
	private CustomerRepository customerRepository;

	@Autowired
	public CustomerService(AuthService authService, AgentRepository agentRepository,
			CustomerRepository customerRepository) {
		this.authService = authService;
		this.agentRepository = agentRepository;
		this.customerRepository = customerRepository;
	}

	public List<CustomerModel> list() {
		
		UserPrinciple principle = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		List<CustomerModel> customers = null;
		if (isAdmin(principle)) {
			customers = (List<CustomerModel>) customerRepository.findAll();
		} else if (UserProfileUtils.isAdminOrAgent(principle)) {
			customers = (List<CustomerModel>) customerRepository.findByAgentId(principle.getId());
		} else {
			LOG_ERROR.error(create("Perfil de Usuario não pode acessar esses dados.").build());
			throw new BusinessException("Perfil de Usuario não pode acessar esses dados.");
		}
		
		return customers;
	}

	@Transactional
	public CustomerModel insert(CustomerRequest request) {

		LOG_ACTION.info(create("Cadastrando Cliente").build());
		
		try {
			validationInsert(request);
			
			UserPrinciple principle = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			Optional<AgentModel> agentOpt = agentRepository.findByUserId(principle.getId());
			if(!agentOpt.isPresent()) {
				LOG_ERROR.error(create("Agente não encontrado").add("agentId", principle.getId()).build());
				throw new NotFoundException("Agente não encontrado");
			}
			
			return customerRepository.save(new CustomerModel(request, agentOpt.get().getUser()));
			
		} catch (BusinessException e) {
			LOG_ERROR.error(create("Erro ao criar o Cliente").add(e).build());
			throw e;
		}
	}

	public CustomerModel getCustomer(Long customerId) {
		Optional<CustomerModel> customerOpt = customerRepository.findById(customerId);
		if (!customerOpt.isPresent()) {
			throw new NotFoundException("Cliente não encontrado com o ID " + customerId);
		}

		return customerOpt.get();
	}

	private void validationInsert(CustomerRequest request) {
		try {
			requireNonNull(request.getEmail(), "Email não pode ser nulo");
			requireNonNull(request.getName(), "Nome não pode ser nulo");
			requireNonNull(request.getTaxId(), "CPF não pode ser nulo");
			isTrue(isValid(request.getTaxId()), "CPF Inválido");
			request.setPassword("");
		}catch (NullPointerException | IllegalArgumentException e) {
			throw new BusinessException(e.getMessage());
		}
	}
}
