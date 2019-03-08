package com.gamaset.crbetadmin.endpoint;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamaset.crbetadmin.schema.request.AgentRequest;
import com.gamaset.crbetadmin.schema.response.AgentResponse;
import com.gamaset.crbetadmin.schema.response.GenericResponse;
import com.gamaset.crbetadmin.service.AgentService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/agents")
public class AgentEndpoint {

	@Autowired
	private AgentService service;
	

	@PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
	@GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
	public GenericResponse<AgentResponse> list() {
		return new GenericResponse<AgentResponse>(service.list());
	}

	@PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
	@PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
	public AgentResponse create(@RequestBody AgentRequest request) {
		return service.insert(request);
	}
}
