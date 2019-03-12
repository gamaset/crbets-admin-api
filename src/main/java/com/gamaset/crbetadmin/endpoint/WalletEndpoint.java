package com.gamaset.crbetadmin.endpoint;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gamaset.crbetadmin.repository.entity.WalletModel;
import com.gamaset.crbetadmin.repository.entity.WalletStatusEnum;
import com.gamaset.crbetadmin.schema.request.WalletUpdateStatusRequest;
import com.gamaset.crbetadmin.schema.response.GenericResponse;
import com.gamaset.crbetadmin.service.WalletService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/wallets")
public class WalletEndpoint {

	@Autowired
	private WalletService service;
	

	@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('AGENT')")
	@GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
	public GenericResponse<WalletModel> list(@RequestParam("status") WalletStatusEnum walletStatus) {
		return new GenericResponse<WalletModel>(service.list(walletStatus));
	}
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
	@PatchMapping(value = "/{walletId}", produces = APPLICATION_JSON_UTF8_VALUE)
	public void list(@PathVariable("walletId") Long walletId, WalletUpdateStatusRequest request) {
		service.updateStatus(walletId, request);
	}

//	@PreAuthorize("hasRole('ADMIN')")
//	@PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
//	public ManagerModel create(@RequestBody ManagerRequest request) {
//		return service.insert(request);
//	}

}
