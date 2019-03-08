package com.gamaset.crbetadmin.endpoint;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gamaset.crbetadmin.repository.entity.WalletStatusEnum;
import com.gamaset.crbetadmin.schema.WalletBalanceSchema;
import com.gamaset.crbetadmin.schema.WalletBalanceTransactionSchema;
import com.gamaset.crbetadmin.schema.response.GenericResponse;
import com.gamaset.crbetadmin.service.WalletBalanceService;
import com.gamaset.crbetadmin.service.WalletBalanceTransactionService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/wallet-balances")
public class WalletBalanceEndpoint {

	@Autowired
	private WalletBalanceService service;
	@Autowired
	private WalletBalanceTransactionService walletTransactionsService;
	

	@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('AGENT')")
	@GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
	public GenericResponse<WalletBalanceSchema> list(@RequestParam(value = "status", required = false) WalletStatusEnum walletStatus) {
		return new GenericResponse<WalletBalanceSchema>(service.list(walletStatus));
	}

	@GetMapping(value = "/{balanceId}/transactions", produces = APPLICATION_JSON_UTF8_VALUE)
	public GenericResponse<WalletBalanceTransactionSchema> list(@PathVariable("balanceId") Long balanceId) {
		return new GenericResponse<WalletBalanceTransactionSchema>(walletTransactionsService.listByBalance(balanceId));
	}

//	@PreAuthorize("hasRole('ADMIN')")
//	@PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
//	public ManagerModel create(@RequestBody ManagerRequest request) {
//		return service.insert(request);
//	}

}
