package com.gamaset.crbetadmin.repository.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "carteira_balanco")
public class WalletBalanceModel extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_carteira_balanco")
	private Long id;

	@Column(name = "valor_orcamento", nullable = false)
	private BigDecimal budget;

	@Column(name = "valor_total_comissao", nullable = false)
	private BigDecimal totalCommissionAmount;

	@Column(name = "valor_total_pago", nullable = false)
	private BigDecimal totalAmountPaid;

	@Column(name = "valor_total_recebido", nullable = false)
	private BigDecimal totalAmountReceived;

	@ManyToOne
	@JoinColumn(name = "id_carteira_fk")
	private WalletModel wallet;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "balance")
	private List<WalletBalanceHistoryModel> histories;

	public WalletBalanceModel() {
	}

	public WalletBalanceModel(WalletModel wallet, BigDecimal initialBudget) {
		setWallet(wallet);
		setBudget(initialBudget);
		setTotalAmountReceived(BigDecimal.ZERO);
		setTotalAmountPaid(BigDecimal.ZERO);
		setTotalCommissionAmount(BigDecimal.ZERO);
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

	public WalletModel getWallet() {
		return wallet;
	}

	public void setWallet(WalletModel wallet) {
		this.wallet = wallet;
	}

	public List<WalletBalanceHistoryModel> getHistories() {
		return histories;
	}

	public void setHistories(List<WalletBalanceHistoryModel> histories) {
		this.histories = histories;
	}

}
