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

import com.gamaset.crbetadmin.repository.entity.EventTypeModel;
import com.gamaset.crbetadmin.schema.request.FlagBooleanUpdateStatusRequest;
import com.gamaset.crbetadmin.schema.response.GenericResponse;
import com.gamaset.crbetadmin.service.EventTypeService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/event-types")
public class EventTypeEndpoint {

	@Autowired
	private EventTypeService service;
	
	@PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
	@GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
	public GenericResponse<EventTypeModel> list() {
		return new GenericResponse<EventTypeModel>(service.listEventTypes());
	}

	@PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
	@PatchMapping(value = "/{eventTypeId}", produces = APPLICATION_JSON_UTF8_VALUE)
	public void updateStatus(@PathVariable("eventTypeId") Long eventTypeId, @RequestBody FlagBooleanUpdateStatusRequest request) {
		service.updateStatus(eventTypeId, request);
	}
	
}
