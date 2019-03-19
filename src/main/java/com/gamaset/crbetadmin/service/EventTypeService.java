package com.gamaset.crbetadmin.service;

import static com.gamaset.crbetadmin.infra.log.LogEvent.create;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamaset.crbetadmin.infra.exception.BusinessException;
import com.gamaset.crbetadmin.infra.exception.NotFoundException;
import com.gamaset.crbetadmin.infra.log.LogEvent;
import com.gamaset.crbetadmin.repository.EventTypeRepository;
import com.gamaset.crbetadmin.repository.entity.EventTypeModel;
import com.gamaset.crbetadmin.schema.request.FlagBooleanUpdateStatusRequest;

@Service
public class EventTypeService {

	private static final Logger LOG_ACTION = LogEvent.logger("ACTION");
	private static final Logger LOG_ERROR = LogEvent.logger("ERROR");
	
	private EventTypeRepository eventTypeRepository;
	private CompetitionService competitionService;

	@Autowired
	public EventTypeService(EventTypeRepository eventTypeRepository, CompetitionService competitionService) {
		this.eventTypeRepository = eventTypeRepository;
		this.competitionService = competitionService;
	}
	
	
	public List<EventTypeModel> listEventTypes(){
		List<EventTypeModel> response = null;
		
		try {
			LOG_ACTION.info(create("Listando Tipos de Evento").build());
			
			response = (List<EventTypeModel>) eventTypeRepository.findAllByOrderByIdAsc();
			
		}catch (Exception e) {
			LOG_ERROR.error(create("Erro ao Listar Tipos de Evento").build());
			throw new BusinessException(e);
		}
		
		return response;
	}

	@Transactional
	public void updateStatus(Long eventTypeId, FlagBooleanUpdateStatusRequest request) {
		try {
			LOG_ACTION.info(create("Atualizando Status do Tipo de Evento").add("eventTypeId", eventTypeId).add("status", request.isStatus()).build());
			
			Optional<EventTypeModel> eventTypeOpt = eventTypeRepository.findById(eventTypeId);
			
			if(!eventTypeOpt.isPresent()) {
				LOG_ERROR.error(create("Tipo de Evento não encontrado").add("eventTypeId", eventTypeId).build());
				throw new NotFoundException("Tipo de Evento não encontrado");
			}
			
			EventTypeModel eventTypeModel = eventTypeOpt.get();
			eventTypeModel.setActive(request.isStatus());
			eventTypeRepository.save(eventTypeModel);

			competitionService.changeStatusForAllCompetitions(eventTypeId, request.isStatus());
			
		}catch (Exception e) {
			LOG_ERROR.error(create("Erro ao Atualizar o Tipo de Evento").build());
			throw new BusinessException(e);
		}
	}

}
