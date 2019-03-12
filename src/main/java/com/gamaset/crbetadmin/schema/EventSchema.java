package com.gamaset.crbetadmin.schema;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.gamaset.crbetadmin.repository.entity.CompetitionModel;
import com.gamaset.crbetadmin.repository.entity.EventModel;

@JsonPropertyOrder({"id", "eventId", "eventName", "eventDate", "competition", "market"})
public class EventSchema {

	private Long id;
	private Long eventId;
	private String eventName;
	private String eventDate;
	private CompetitionModel competition;
	private MarketSchema market;

	public EventSchema(EventModel model) {
		this.id = model.getId();
		this.eventId = model.getEventId();
		this.eventName = model.getEventName();
		this.competition = model.getCompetition();
		this.market = new MarketSchema(model.getMarket());
		this.eventDate = model.getEventDate();
	}

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

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public CompetitionModel getCompetition() {
		return competition;
	}

	public void setCompetition(CompetitionModel competition) {
		this.competition = competition;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public MarketSchema getMarket() {
		return market;
	}

	public void setMarket(MarketSchema market) {
		this.market = market;
	}

}
