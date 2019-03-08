package com.gamaset.crbetadmin.repository.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "evento")
public class EventModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "id_evento", nullable = false)
	private Long eventId;

	@Column(name = "evento_descricao", nullable = false)
	private String eventName;

	@JoinColumn(name = "id_competicao_fk", nullable = false)
	@ManyToOne
	private CompetitionModel competition;

	@JoinColumn(name = "id_aposta_fk", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private BetModel bet;

	@JoinColumn(name = "id_mercado_fk", nullable = false)
	@ManyToOne(cascade = CascadeType.ALL)
	private MarketModel market;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String description) {
		this.eventName = description;
	}

	public CompetitionModel getCompetition() {
		return competition;
	}

	public void setCompetition(CompetitionModel competition) {
		this.competition = competition;
	}

	public BetModel getBet() {
		return bet;
	}

	public void setBet(BetModel bet) {
		this.bet = bet;
	}

	public MarketModel getMarket() {
		return market;
	}

	public void setMarket(MarketModel market) {
		this.market = market;
	}

//	@Override
//	public String toString() {
//		return ToStringBuilder.reflectionToString(this);
//	}
}
