package com.gamaset.crbetadmin.endpoint;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamaset.crbetadmin.schema.request.BetRequest;
import com.gamaset.crbetadmin.schema.response.BetResponse;
import com.gamaset.crbetadmin.service.BetService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/bet")
public class BetEndpoint {

	@Autowired
	private BetService service;
	
	@PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
	public BetResponse create(@RequestBody BetRequest request) {
		return service.createBet(request);
	}

}
