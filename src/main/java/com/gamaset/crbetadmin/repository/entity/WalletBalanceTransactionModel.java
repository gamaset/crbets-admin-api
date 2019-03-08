package com.gamaset.crbetadmin.repository.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "carteira_balanco_transacao")
public class WalletBalanceTransactionModel extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_carteira_balanco_transacao")
	private Long id;

	@Column(name = "valor_transacao", nullable = false)
	private BigDecimal amount;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_transacao", nullable = false)
	private TransactionType transactionType;

	@ManyToOne
	@JoinColumn(name = "id_carteira_balanco_fk")
	private WalletBalanceModel walletBalance;

	public WalletBalanceTransactionModel() {
	}

	public WalletBalanceTransactionModel(BigDecimal amount, TransactionType transactionType, WalletBalanceModel walletBalance) {
		this.amount = amount;
		this.transactionType = transactionType;
		this.walletBalance = walletBalance;
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

	public WalletBalanceModel getWalletBalance() {
		return walletBalance;
	}

	public void setWalletBalance(WalletBalanceModel walletBalance) {
		this.walletBalance = walletBalance;
	}

}
