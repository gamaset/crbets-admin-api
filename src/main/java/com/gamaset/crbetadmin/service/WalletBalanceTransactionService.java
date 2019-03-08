package com.gamaset.crbetadmin.service;

import static com.gamaset.crbetadmin.repository.entity.TransactionType.CREDIT_AMOUNT_COMMISION;
import static com.gamaset.crbetadmin.repository.entity.TransactionType.CREDIT_AMOUNT_PAID;
import static com.gamaset.crbetadmin.repository.entity.TransactionType.CREDIT_AMOUNT_RECEIVED;
import static com.gamaset.crbetadmin.repository.entity.TransactionType.DEBIT_BUDGET;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gamaset.crbetadmin.infra.log.LogEvent;
import com.gamaset.crbetadmin.repository.WalletBalanceTransactionRepository;
import com.gamaset.crbetadmin.repository.entity.WalletBalanceModel;
import com.gamaset.crbetadmin.repository.entity.WalletBalanceTransactionModel;
import com.gamaset.crbetadmin.schema.WalletBalanceTransactionSchema;

@Service
public class WalletBalanceTransactionService {

	private static final Logger LOG_ACTION = LogEvent.logger("ACTION");

	@Autowired
	private WalletBalanceTransactionRepository walletBalanceTransactionRepository;

	@Transactional
	public void createTransactionToBetRegistered(BigDecimal amount, WalletBalanceModel walletBalance) {
		LOG_ACTION.info(LogEvent.create("Criando transação de aposta REGISTRADA na carteira do agente")
				.add("walletId", walletBalance.getWallet().getId())
				.add("agentId", walletBalance.getWallet().getAgent().getId()).add("amount", amount).build());
		
		walletBalanceTransactionRepository.save(new WalletBalanceTransactionModel(amount, DEBIT_BUDGET, walletBalance));
		walletBalanceTransactionRepository
				.save(new WalletBalanceTransactionModel(amount, CREDIT_AMOUNT_RECEIVED, walletBalance));
	}

	public void createTransactionToBetWon(BigDecimal profit, BigDecimal commisionAmount,
			WalletBalanceModel walletBalance) {
		LOG_ACTION.info(LogEvent.create("Criando transação de aposta VENCIDA na carteira do agente")
				.add("walletId", walletBalance.getWallet().getId())
				.add("agentId", walletBalance.getWallet().getAgent().getId())
				.add("profit", profit).add("commision", commisionAmount)
				.build());
		
		walletBalanceTransactionRepository
				.save(new WalletBalanceTransactionModel(commisionAmount, CREDIT_AMOUNT_COMMISION, walletBalance));
		walletBalanceTransactionRepository
				.save(new WalletBalanceTransactionModel(profit, CREDIT_AMOUNT_PAID, walletBalance));
	}

	public List<WalletBalanceTransactionSchema> listByBalance(Long balanceId) {
		List<WalletBalanceTransactionSchema> response = new ArrayList<>(); 
		List<WalletBalanceTransactionModel> transactions = walletBalanceTransactionRepository.findByWalletBalanceId(balanceId);
		
		if(Objects.nonNull(transactions) && transactions.size() > 0) {
			response = transactions.stream().map(t -> new WalletBalanceTransactionSchema(t)).collect(Collectors.toList());
		}
		
		return response;
	}

}
