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

import com.gamaset.crbetadmin.repository.entity.CompetitionModel;
import com.gamaset.crbetadmin.schema.request.FlagBooleanUpdateStatusRequest;
import com.gamaset.crbetadmin.schema.response.GenericResponse;
import com.gamaset.crbetadmin.service.CompetitionService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/competitions")
public class CompetitionEndpoint {

	@Autowired
	private CompetitionService service;
	
	@PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
	@GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
	public GenericResponse<CompetitionModel> list() {
		return new GenericResponse<CompetitionModel>(service.listCompetitions());
	}
	

	@PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
	@PatchMapping(value = "/{competitionId}", produces = APPLICATION_JSON_UTF8_VALUE)
	public void updateStatus(@PathVariable("competitionId") Long competitionId, @RequestBody FlagBooleanUpdateStatusRequest request) {
		service.updateStatus(competitionId, request);
	}
	
}
