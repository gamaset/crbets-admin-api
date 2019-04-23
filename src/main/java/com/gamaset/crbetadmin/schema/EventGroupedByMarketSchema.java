package com.gamaset.crbetadmin.schema;

public class EventGroupedByMarketSchema {

	private Long eventId;
	private String eventName;
	private String eventDate;
	private String marketName;
	private Long marketSelectionId;
	private String marketSelectionName;
	private String status;
	private Integer numTips;

	public EventGroupedByMarketSchema(Long eventId, String eventName, String eventDate, String marketName,
			Long marketSelectionId, String marketSelectionName, String status, Integer numTips) {
		this.eventId = eventId;
		this.eventName = eventName;
		this.eventDate = eventDate;
		this.marketName = marketName;
		this.marketSelectionId = marketSelectionId;
		this.marketSelectionName = marketSelectionName;
		this.status = status;
		this.numTips = numTips;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
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

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public Long getMarketSelectionId() {
		return marketSelectionId;
	}

	public void setMarketSelectionId(Long marketSelectionId) {
		this.marketSelectionId = marketSelectionId;
	}

	public String getMarketSelectionName() {
		return marketSelectionName;
	}

	public void setMarketSelectionName(String marketSelectionName) {
		this.marketSelectionName = marketSelectionName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getNumTips() {
		return numTips;
	}

	public void setNumTips(Integer numTips) {
		this.numTips = numTips;
	}

}
