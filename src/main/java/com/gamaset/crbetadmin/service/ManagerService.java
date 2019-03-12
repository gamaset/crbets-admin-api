package com.gamaset.crbetadmin.service;

import static com.gamaset.crbetadmin.infra.log.LogEvent.create;
import static com.gamaset.crbetadmin.infra.utils.CPFValidator.isValid;
import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.isTrue;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gamaset.crbetadmin.infra.configuration.security.AuthService;
import com.gamaset.crbetadmin.infra.exception.BusinessException;
import com.gamaset.crbetadmin.infra.log.LogEvent;
import com.gamaset.crbetadmin.repository.ManagerRepository;
import com.gamaset.crbetadmin.repository.entity.ManagerModel;
import com.gamaset.crbetadmin.repository.entity.UserModel;
import com.gamaset.crbetadmin.schema.request.ManagerRequest;
import com.gamaset.crbetadmin.schema.request.SignUpRequest;

@Service
public class ManagerService {
	

	private static final Logger LOG_ACTION = LogEvent.logger("ACTION");
	private static final Logger LOG_ERROR = LogEvent.logger("ERROR");

	private ManagerRepository managerRepository;
	private AuthService authService;

	@Autowired
	public ManagerService(ManagerRepository managerRepository, AuthService authService) {
		this.managerRepository = managerRepository;
		this.authService = authService;
	}

	public List<ManagerModel> list() {
		LOG_ACTION.info(create("Listando Gerentes").build());
		return (List<ManagerModel>) managerRepository.findAll();
	}

	@Transactional
	public ManagerModel insert(ManagerRequest request) {
		LOG_ACTION.info(create("Cadastrando Gerente").build());
		ManagerModel response = null;
		try {
			validateInsert(request);
			UserModel userCreated = authService.signUp(new SignUpRequest(request, "MANAGER"));
			response = managerRepository.save(new ManagerModel(userCreated));
		} catch (BusinessException e) {
			LOG_ERROR.error(create("Erro ao criar Gerente").add(e).build());
			throw e;
		}

		return response;
	}

	private void validateInsert(ManagerRequest request) {
		try {
			requireNonNull(request.getEmail(), "Email não pode ser nulo");
			requireNonNull(request.getName(), "Nome não pode ser nulo");
			requireNonNull(request.getPassword(), "Senha não pode ser nulo");
			requireNonNull(request.getTaxId(), "CPF não pode ser nulo");
			isTrue(isValid(request.getTaxId()), "CPF Inválido");
			requireNonNull(request.getUsername(), "Nome de Usuario não pode ser nulo");
		} catch (NullPointerException | IllegalArgumentException e) {
			throw new BusinessException(e.getMessage());
		}

	}
}
