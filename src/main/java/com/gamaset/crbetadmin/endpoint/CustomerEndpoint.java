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

import com.gamaset.crbetadmin.repository.entity.CustomerModel;
import com.gamaset.crbetadmin.schema.request.CustomerRequest;
import com.gamaset.crbetadmin.schema.response.GenericResponse;
import com.gamaset.crbetadmin.service.CustomerService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/customers")
public class CustomerEndpoint {

	@Autowired
	private CustomerService service;
	
	@PreAuthorize("hasRole('AGENT') or hasRole('MANAGER') or hasRole('ADMIN')")
	@GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
	public GenericResponse<CustomerModel> list() {
		return new GenericResponse<CustomerModel>(service.list());
	}
	
	@PreAuthorize("hasRole('AGENT') or hasRole('MANAGER') or hasRole('ADMIN')")
	@PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
	public CustomerModel create(@RequestBody CustomerRequest request) {
		return service.insert(request);
	}

}
