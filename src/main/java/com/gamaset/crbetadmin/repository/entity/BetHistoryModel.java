package com.gamaset.crbetadmin.repository.entity;

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

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "aposta_historico")
public class BetHistoryModel extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_aposta_historico")
	private Long id;
	@JoinColumn(name = "id_aposta_fk", nullable = false)
	@ManyToOne
	private BetModel bet;
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private BetStatusEnum status;

	public BetHistoryModel(BetModel bet, BetStatusEnum status) {
		this.bet = bet;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BetModel getBet() {
		return bet;
	}

	public void setBet(BetModel bet) {
		this.bet = bet;
	}

	public BetStatusEnum getStatus() {
		return status;
	}

	public void setStatus(BetStatusEnum status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
