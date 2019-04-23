package com.gamaset.crbetadmin.schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gamaset.crbetadmin.repository.entity.WalletBalanceModel;

public class WalletBalanceSchema {

	private Long id;
	private BigDecimal budget;
	private BigDecimal percentComission;
	private BigDecimal totalCommissionAmount;
	private BigDecimal totalAmountPaid;
	private BigDecimal totalAmountReceived;
	private WalletSchema wallet;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime lastDateUpdate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime createdDate;

	public WalletBalanceSchema() {
	}

	public WalletBalanceSchema(WalletBalanceModel walletBalance) {
		setId(walletBalance.getId());
		setBudget(walletBalance.getBudget());
		setTotalAmountPaid(walletBalance.getTotalAmountPaid());
		setTotalAmountReceived(walletBalance.getTotalAmountReceived());
		setTotalCommissionAmount(walletBalance.getTotalCommissionAmount());
		setWallet(new WalletSchema(walletBalance.getWallet()));
		setLastDateUpdate(walletBalance.getUpdateAt());
		setCreatedDate(walletBalance.getCreateAt());
		setPercentComission(walletBalance.getPercentComission());
	}

	public LocalDateTime getLastDateUpdate() {
		return lastDateUpdate;
	}

	public void setLastDateUpdate(LocalDateTime lastDateUpdate) {
		this.lastDateUpdate = lastDateUpdate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getBudget() {
		return budget;
	}

	public void setBudget(BigDecimal budget) {
		this.budget = budget;
	}

	public BigDecimal getTotalCommissionAmount() {
		return totalCommissionAmount;
	}

	public void setTotalCommissionAmount(BigDecimal totalCommissionAmount) {
		this.totalCommissionAmount = totalCommissionAmount;
	}

	public BigDecimal getTotalAmountPaid() {
		return totalAmountPaid;
	}

	public void setTotalAmountPaid(BigDecimal totalAmountPaid) {
		this.totalAmountPaid = totalAmountPaid;
	}

	public BigDecimal getTotalAmountReceived() {
		return totalAmountReceived;
	}

	public void setTotalAmountReceived(BigDecimal totalAmountReceived) {
		this.totalAmountReceived = totalAmountReceived;
	}

	public WalletSchema getWallet() {
		return wallet;
	}

	public void setWallet(WalletSchema wallet) {
		this.wallet = wallet;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public BigDecimal getPercentComission() {
		return percentComission;
	}

	public void setPercentComission(BigDecimal percentComission) {
		this.percentComission = percentComission;
	}

}
