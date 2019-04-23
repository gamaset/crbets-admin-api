package com.gamaset.crbetadmin.executor;

import static com.gamaset.crbetadmin.infra.log.LogEvent.create;
import static com.gamaset.crbetadmin.repository.entity.BetStatusEnum.LOSE;
import static com.gamaset.crbetadmin.repository.entity.BetStatusEnum.WON;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;

import com.gamaset.crbetadmin.infra.log.LogEvent;
import com.gamaset.crbetadmin.repository.BetRepository;
import com.gamaset.crbetadmin.repository.EventRepository;
import com.gamaset.crbetadmin.repository.entity.BetModel;
import com.gamaset.crbetadmin.repository.entity.BetStatusEnum;
import com.gamaset.crbetadmin.repository.entity.EventModel;
import com.gamaset.crbetadmin.schema.request.BetUpdateStatusRequest;
import com.gamaset.crbetadmin.service.BetService;

public class TicketUpdateExecutor implements Runnable {

	private static final Logger LOG_ACTION = LogEvent.logger("ACTION");
	
	private BetService betService;
	private BetRepository betRepository;
	private EventRepository eventRepository;
	
	public TicketUpdateExecutor(BetService betService, EventRepository eventRepository, BetRepository betRepository) {
		this.betService = betService;
		this.eventRepository = eventRepository;
		this.betRepository = betRepository;
	}

	@Override
	public void run() {
		updateBetsRegistering();
	}

	public void updateBetsRegistering() {
		
		LOG_ACTION.info(create("Atualizando Status dos Tickets..").build());

		List<BetModel> bets = betRepository.findByStatus(BetStatusEnum.REGISTERING);
		if (Objects.nonNull(bets)) {
			for (BetModel bet : bets) {
				int wonCount = 0;
				int loseCount = 0;
				List<EventModel> events = eventRepository.findByBetId(bet.getId());
				for (EventModel event : events) {
					if (event.getMarket().getPrice().getStatus().equals(WON)) {
						wonCount++;
					}else if(event.getMarket().getPrice().getStatus().equals(LOSE)) {
						loseCount++;
					}else {
						break;
					}
				}
				
				if(wonCount == events.size()) {
					betService.updateStatusBetWithoutSecurity(bet.getId(), new BetUpdateStatusRequest(BetStatusEnum.WON.ordinal()));
				}else if((loseCount + wonCount) == events.size()) {
					betService.updateStatusBetWithoutSecurity(bet.getId(), new BetUpdateStatusRequest(BetStatusEnum.LOSE.ordinal()));
				}
			}
		}
		
		LOG_ACTION.info(create("Atualização de status dos Tickets Finalizado.").build());
	}
	
}
