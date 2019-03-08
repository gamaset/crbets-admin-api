package com.gamaset.crbetadmin.repository.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "carteira_balanco_historico")
public class WalletBalanceHistoryModel extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_carteira_balanco")
	private Long id;

	@Column(name = "valor_orcamento_anterior")
	private BigDecimal budgetBefore;
	@Column(name = "valor_orcamento_atual")
	private BigDecimal budgetActual;
	@Column(name = "valor_total_comissao_anterior")
	private BigDecimal totalCommissionAmountBefore;
	@Column(name = "valor_total_comissao_atual")
	private BigDecimal totalCommissionAmountActual;
	@Column(name = "valor_total_pago_anterior")
	private BigDecimal totalAmountPaidBefore;
	@Column(name = "valor_total_pago_atual")
	private BigDecimal totalAmountPaidActual;
	@Column(name = "valor_total_recebido_anterior")
	private BigDecimal totalAmountReceivedBefore;
	@Column(name = "valor_total_recebido_atual")
	private BigDecimal totalAmountReceivedActual;

	@ManyToOne
	@JoinColumn(name = "id_carteira_balanco_fk")
	private WalletBalanceModel balance;

	public WalletBalanceHistoryModel() {
	}

	
	
	public WalletBalanceHistoryModel(WalletBalanceModel balanceBefore, WalletBalanceModel balanceActual) {
		setBudgetBefore(balanceBefore.getBudget());
		setBudgetActual(balanceActual.getBudget());
		
		setTotalCommissionAmountBefore(balanceBefore.getTotalCommissionAmount());
		setTotalCommissionAmountActual(balanceActual.getTotalCommissionAmount());
		
		setTotalAmountPaidBefore(balanceBefore.getTotalAmountPaid());
		setTotalAmountPaidActual(balanceActual.getTotalAmountPaid());
	
		setTotalAmountReceivedBefore(balanceBefore.getTotalAmountReceived());
		setTotalAmountReceivedActual(balanceActual.getTotalAmountReceived());
		
		setBalance(balanceActual);
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getBudgetBefore() {
		return budgetBefore;
	}

	public void setBudgetBefore(BigDecimal budgetBefore) {
		this.budgetBefore = budgetBefore;
	}

	public BigDecimal getBudgetActual() {
		return budgetActual;
	}

	public void setBudgetActual(BigDecimal budgetActual) {
		this.budgetActual = budgetActual;
	}

	public BigDecimal getTotalCommissionAmountBefore() {
		return totalCommissionAmountBefore;
	}

	public void setTotalCommissionAmountBefore(BigDecimal totalCommissionAmountBefore) {
		this.totalCommissionAmountBefore = totalCommissionAmountBefore;
	}

	public BigDecimal getTotalCommissionAmountActual() {
		return totalCommissionAmountActual;
	}

	public void setTotalCommissionAmountActual(BigDecimal totalCommissionAmountActual) {
		this.totalCommissionAmountActual = totalCommissionAmountActual;
	}

	public BigDecimal getTotalAmountPaidBefore() {
		return totalAmountPaidBefore;
	}

	public void setTotalAmountPaidBefore(BigDecimal totalAmountPaidBefore) {
		this.totalAmountPaidBefore = totalAmountPaidBefore;
	}

	public BigDecimal getTotalAmountPaidActual() {
		return totalAmountPaidActual;
	}

	public void setTotalAmountPaidActual(BigDecimal totalAmountPaidActual) {
		this.totalAmountPaidActual = totalAmountPaidActual;
	}

	public BigDecimal getTotalAmountReceivedBefore() {
		return totalAmountReceivedBefore;
	}

	public void setTotalAmountReceivedBefore(BigDecimal totalAmountReceivedBefore) {
		this.totalAmountReceivedBefore = totalAmountReceivedBefore;
	}

	public BigDecimal getTotalAmountReceivedActual() {
		return totalAmountReceivedActual;
	}

	public void setTotalAmountReceivedActual(BigDecimal totalAmountReceivedActual) {
		this.totalAmountReceivedActual = totalAmountReceivedActual;
	}

	public WalletBalanceModel getBalance() {
		return balance;
	}

	public void setBalance(WalletBalanceModel balance) {
		this.balance = balance;
	}

}
