package com.gamaset.crbetadmin.repository.entity;

import java.util.List;

import javax.persistence.CascadeType;
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
@Table(name = "carteira")
public class WalletModel extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_carteira", nullable = false)
	private Long id;

	@Column(name = "status", nullable = false)
	private WalletStatusEnum status;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "wallet")
	private List<WalletBalanceModel> balances;

	@ManyToOne
	@JoinColumn(name = "id_agente_fk")
	private AgentModel agent;

	public WalletModel() {
	}

	public WalletModel(AgentModel agent) {
		setStatus(WalletStatusEnum.OPEN);
		setAgent(agent);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public WalletStatusEnum getStatus() {
		return status;
	}

	public void setStatus(WalletStatusEnum status) {
		this.status = status;
	}

	public AgentModel getAgent() {
		return agent;
	}

	public void setAgent(AgentModel agent) {
		this.agent = agent;
	}

	public List<WalletBalanceModel> getBalances() {
		return balances;
	}

	public void setBalances(List<WalletBalanceModel> balances) {
		this.balances = balances;
	}

}
