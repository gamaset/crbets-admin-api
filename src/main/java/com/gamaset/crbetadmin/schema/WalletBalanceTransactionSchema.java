package com.gamaset.crbetadmin.schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gamaset.crbetadmin.repository.entity.TransactionType;
import com.gamaset.crbetadmin.repository.entity.WalletBalanceTransactionModel;

public class WalletBalanceTransactionSchema {

	private Long id;
	private BigDecimal amount;
	private TransactionType transactionType;
	private WalletBalanceSchema walletBalance;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime createdDate;
	
	public WalletBalanceTransactionSchema() {
	}

	public WalletBalanceTransactionSchema(WalletBalanceTransactionModel transaction) {
		setId(transaction.getId());
		setAmount(transaction.getAmount());
		setTransactionType(transaction.getTransactionType());
		setWalletBalance(new WalletBalanceSchema(transaction.getWalletBalance()));
		setCreatedDate(transaction.getCreateAt());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public WalletBalanceSchema getWalletBalance() {
		return walletBalance;
	}

	public void setWalletBalance(WalletBalanceSchema walletBalance) {
		this.walletBalance = walletBalance;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime created_at) {
		this.createdDate = created_at;
	}

}
