package com.gamaset.crbetadmin.service;

import static com.gamaset.crbetadmin.infra.log.LogEvent.create;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gamaset.crbetadmin.executor.TicketUpdateExecutor;
import com.gamaset.crbetadmin.infra.exception.BusinessException;
import com.gamaset.crbetadmin.infra.log.LogEvent;
import com.gamaset.crbetadmin.repository.BetRepository;
import com.gamaset.crbetadmin.repository.EventRepository;
import com.gamaset.crbetadmin.repository.MarketSelectionRepository;
import com.gamaset.crbetadmin.repository.entity.BetStatusEnum;
import com.gamaset.crbetadmin.repository.entity.MarketSelectionModel;
import com.gamaset.crbetadmin.schema.EventGroupedByMarketSchema;
import com.gamaset.crbetadmin.schema.request.BetUpdateStatusRequest;

@Service
public class EventService {
	
	private static final Logger LOG_ACTION = LogEvent.logger("ACTION");
	private static final Logger LOG_ERROR = LogEvent.logger("ERROR");

	@Autowired
	private BetRepository betRepository;
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private MarketSelectionRepository marketSelectionRepository;
	@Autowired
	private BetService betService;
	
	public List<EventGroupedByMarketSchema> listEventsWithMarket() {
		
		List<Object[]> eventsObjs = eventRepository.listEventsGroupedByEventAndMarket();
		List<EventGroupedByMarketSchema> events = convertToList(eventsObjs);
		
		return events;
	}
	
	private List<EventGroupedByMarketSchema> convertToList(List<Object[]> eventsObjs){
		List<EventGroupedByMarketSchema> events = new ArrayList<EventGroupedByMarketSchema>();
		eventsObjs.forEach(obj -> {
			events.add(new EventGroupedByMarketSchema(Long.valueOf(obj[0].toString()), String.valueOf(obj[1]), String.valueOf(obj[2]), String.valueOf(obj[3]), 
					Long.valueOf(obj[4].toString()), String.valueOf(obj[5]), String.valueOf(obj[6]), Integer.valueOf(obj[7].toString())));
		});
		
		return events;
	}

	@Transactional
	public void updateSelectionStatus(Long selectionId, BetUpdateStatusRequest request) {
		
		LOG_ACTION.info(create("Atualizando Mercado de Seleção").add("selectionId", selectionId).build());

		List<MarketSelectionModel> selections = marketSelectionRepository.findBySelectionId(selectionId);
		
		if (Objects.isNull(selections) || selections.isEmpty()) {
			LOG_ERROR.error(create("Seleção não encontrada").add("selectionId", selectionId).build());
			throw new BusinessException(String.format("Seleção não encontrada ID %s", selectionId));
		} else {
			selections.forEach(selec -> {
				MarketSelectionModel selectionModel = selec;
				selectionModel.setStatus(BetStatusEnum.valueOf(request.getStatus()).get());
				marketSelectionRepository.save(selectionModel);
			});
			
			Thread t1 = new Thread(new TicketUpdateExecutor(betService, eventRepository, betRepository));
			t1.start();
			
		}
		
	}
	
	
}
