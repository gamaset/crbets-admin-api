package com.gamaset.crbetadmin.endpoint;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamaset.crbetadmin.schema.EventGroupedByMarketSchema;
import com.gamaset.crbetadmin.schema.request.BetUpdateStatusRequest;
import com.gamaset.crbetadmin.schema.response.GenericResponse;
import com.gamaset.crbetadmin.service.EventService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/tickets/selections")
public class TicketSelectionEndpoint {

	@Autowired
	private EventService service;
	
	@PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
	@GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
	public GenericResponse<EventGroupedByMarketSchema> list() {
		return new GenericResponse<EventGroupedByMarketSchema>(service.listEventsWithMarket());
	}

	@PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
	@PatchMapping(value = "/{selectionId}", produces = APPLICATION_JSON_UTF8_VALUE)
	public void updateStatus(@PathVariable("selectionId") Long selectionId, @RequestBody BetUpdateStatusRequest request) {
		service.updateSelectionStatus(selectionId, request);
	}

	
}
