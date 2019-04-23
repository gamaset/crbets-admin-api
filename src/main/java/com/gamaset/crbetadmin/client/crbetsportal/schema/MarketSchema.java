package com.gamaset.crbetadmin.client.crbetsportal.schema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MarketSchema implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String marketId;
	private String marketName;
	private List<PriceMarketSchema> prices;
	
	public MarketSchema() {
	}

	public MarketSchema(String marketId, String marketName) {
		this.marketId = marketId;
		this.marketName = marketName;
		this.prices = new ArrayList<>();
	}

	public MarketSchema withPrices(List<Runner> runners, List<MarketCatalogue> listMarketCatalogue) {
		for (Runner runner : runners) {
			for (MarketCatalogue mc : listMarketCatalogue) {
				if (mc.getMarketId().equals(marketId)) {

					for (RunnerCatalog rn : mc.getRunners()) {
						if (runner.getSelectionId().equals(rn.getSelectionId())) {
							PriceMarketSchema pm = new PriceMarketSchema();
							if (runner.getEx().getAvailableToBack().size() > 0) {
								Double price = runner.getEx().getAvailableToBack()
								.get(runner.getEx().getAvailableToBack().size() - 1).getPrice();
								pm.setOdd(price);
							}
							pm.setSelectionId(rn.getSelectionId());
							pm.setSelectionName(rn.getRunnerName());
							this.prices.add(pm);
						}

					}
				}
			}
		}
		return this;
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

	public List<PriceMarketSchema> getPrices() {
		return prices;
	}

	public void setPrices(List<PriceMarketSchema> prices) {
		this.prices = prices;
	}
}
