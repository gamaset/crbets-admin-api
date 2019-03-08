package com.gamaset.crbetadmin.schema.request;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.gamaset.crbetadmin.repository.entity.EventModel;

public class BetRequest {

	private BigDecimal betValue;
	@NotNull
	@Size(min = 11, max = 11)
	private String taxId;
	private List<EventModel> events;

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public BigDecimal getBetValue() {
		return betValue;
	}

	public void setBetValue(BigDecimal betValue) {
		this.betValue = betValue;
	}

	public List<EventModel> getEvents() {
		return events;
	}

	public void setEvents(List<EventModel> events) {
		this.events = events;
	}

}
