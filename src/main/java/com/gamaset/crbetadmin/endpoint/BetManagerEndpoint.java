package com.gamaset.crbetadmin.endpoint;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamaset.crbetadmin.schema.request.BetUpdateStatusRequest;
import com.gamaset.crbetadmin.schema.response.BetResponse;
import com.gamaset.crbetadmin.schema.response.GenericResponse;
import com.gamaset.crbetadmin.service.BetService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/bets")
public class BetManagerEndpoint {

	@Autowired
	private BetService service;
	
	@PreAuthorize("hasRole('AGENT') or hasRole('CUSTOMER') or hasRole('ADMIN')")
	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public GenericResponse<BetResponse> list() {
		return new GenericResponse<BetResponse>(service.list());
	}

	@PreAuthorize("hasRole('AGENT') or hasRole('ADMIN')")
	@PatchMapping(value = "/{betId}", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
	public void updateStatus(@PathVariable("betId") Long betId, @RequestBody BetUpdateStatusRequest request) {
		service.updateStatusBet(betId, request);
	}

	@PreAuthorize("hasRole('AGENT') or hasRole('MANAGER') or hasRole('ADMIN')")
	@GetMapping(value = "/{betId}", produces = APPLICATION_JSON_UTF8_VALUE)
	public BetResponse getBet(@PathVariable("betId") Long betId) {
		return service.getBet(betId);
	}
}
