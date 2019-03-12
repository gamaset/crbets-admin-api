package com.gamaset.crbetadmin.schema;

import com.gamaset.crbetadmin.repository.entity.MarketModel;
import com.gamaset.crbetadmin.repository.entity.MarketSelectionModel;

public class MarketSchema {

	private Long id;
	private String marketId;
	private String marketName;
	private MarketSelectionModel marketSelection;

	public MarketSchema() {
	}

	public MarketSchema(MarketModel market) {
		this.id = market.getId();
		this.marketId = market.getMarketId();
		this.marketName = market.getMarketName();
		this.marketSelection = market.getPrice();
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMarketId() {
		return marketId;
	}
	public void setMarketId(String marketId) {
		this.marketId = marketId;
	}
	public String getMarketName() {
		return marketName;
	}
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
	public MarketSelectionModel getMarketSelection() {
		return marketSelection;
	}
	public void setMarketSelection(MarketSelectionModel price) {
		this.marketSelection = price;
	}
	
}
