package com.gamaset.crbetadmin.service;

import static com.gamaset.crbetadmin.infra.log.LogEvent.create;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamaset.crbetadmin.infra.exception.BusinessException;
import com.gamaset.crbetadmin.infra.exception.NotFoundException;
import com.gamaset.crbetadmin.infra.log.LogEvent;
import com.gamaset.crbetadmin.repository.CompetitionRepository;
import com.gamaset.crbetadmin.repository.entity.CompetitionModel;
import com.gamaset.crbetadmin.schema.request.FlagBooleanUpdateStatusRequest;

@Service
public class CompetitionService {

	private static final Logger LOG_ACTION = LogEvent.logger("ACTION");
	private static final Logger LOG_ERROR = LogEvent.logger("ERROR");
	
	private CompetitionRepository competitionRepository;

	@Autowired
	public CompetitionService(CompetitionRepository competitionRepository) {
		this.competitionRepository = competitionRepository;
	}
	
	
	public List<CompetitionModel> listCompetitions(){
		List<CompetitionModel> response = null;
		
		try {
			LOG_ACTION.error(create("Listando Competicoes").build());
			
			response = (List<CompetitionModel>) competitionRepository.findAllByOrderByEventTypeIdAscDescriptionAsc();
			
		}catch (Exception e) {
			LOG_ERROR.error(create("Erro ao Listar Competicoes").build());
			throw new BusinessException(e);
		}
		
		return response;
	}


	public void updateStatus(Long competitionId, FlagBooleanUpdateStatusRequest request) {
		try {
			LOG_ACTION.error(create("Atualizando Status da Competicao").add("competitionId", competitionId).add("status", request.isStatus()).build());
			
			Optional<CompetitionModel> competitionOpt = competitionRepository.findById(competitionId);
			
			if(!competitionOpt.isPresent()) {
				LOG_ERROR.error(create("Competicao não encontrada").add("competitionId", competitionId).build());
				throw new NotFoundException("Competicao não encontrada");
			}
			
			CompetitionModel competitionModel = competitionOpt.get();
			competitionModel.setActive(request.isStatus());
			
			competitionRepository.save(competitionModel);
			
		}catch (Exception e) {
			LOG_ERROR.error(create("Erro ao Atualizar a Competicao").build());
			throw new BusinessException(e);
		}
		
	}
	
}
