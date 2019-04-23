package com.gamaset.crbetadmin.endpoint;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamaset.crbetadmin.executor.TicketUpdateExecutor;
import com.gamaset.crbetadmin.repository.BetRepository;
import com.gamaset.crbetadmin.repository.EventRepository;
import com.gamaset.crbetadmin.service.BetService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/batch")
public class BatchEndpoint {

	private BetService betService;
	private BetRepository betRepository;
	private EventRepository eventRepository;
	
	@Autowired
	public BatchEndpoint(BetService betService, EventRepository eventRepository, BetRepository betRepository) {
		this.betService = betService;
		this.eventRepository = eventRepository;
		this.betRepository = betRepository;
	}
	
	
	@PostMapping(value = "/update-ticket-status", produces = APPLICATION_JSON_UTF8_VALUE)
	public void updateTicketStatusBatch() {
		Thread t1 = new Thread(new TicketUpdateExecutor(betService, eventRepository, betRepository));
		t1.start();
	}

	
}
