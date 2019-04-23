package com.gamaset.crbetadmin.client.crbetsportal.schema;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CompetitionEventsResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private CompetitionSchema competition;
	private List<EventSchema> events;

	public CompetitionEventsResponse() {
		// TODO Auto-generated constructor stub
	}

	public CompetitionEventsResponse(List<EventSchema> events) {
		this.events = events;
	}

	public void buildEventMarketCatalogue(List<MarketCatalogue> listMarketCatalogue) {
		if (Objects.nonNull(getEvents()) && Objects.nonNull(listMarketCatalogue)) {
			for (EventSchema event : getEvents()) {
				for (MarketCatalogue marketCatalogue : listMarketCatalogue) {
					if (marketCatalogue.getEvent().getId().equalsIgnoreCase(event.getId())) {
						event.addMarket(marketCatalogue);
					}
				}
			}
		}
	}

	public void buildEventMarketPrices(List<MarketBook> listMarketBook) {
		if (Objects.nonNull(getEvents()) && Objects.nonNull(listMarketBook)) {
			for (EventSchema event : getEvents()) {
				for (MarketSchema marketSchema : event.getMarkets()) {
					for (MarketBook marketBook : listMarketBook) {
						if (marketSchema.getMarketId().equalsIgnoreCase(marketBook.getMarketId())) {
							for (PriceMarketSchema priceMarketSchema : marketSchema.getPrices()) {
								for (Runner runner : marketBook.getRunners()) {
									if (runner.getSelectionId().equals(priceMarketSchema.getSelectionId())) {
										Optional<PriceSize> priceOpt = runner.getEx().getAvailableToBack().stream()
												.findFirst();
										if (priceOpt.isPresent()) {
											priceMarketSchema.setOdd(priceOpt.get().getPrice());
										}else {
											priceMarketSchema.setOdd(1.00d);
										}
										break;
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public void setCompetition(CompetitionSchema competition) {
		this.competition = competition;
	}

	public CompetitionSchema getCompetition() {
		return this.competition;
	}

	public List<EventSchema> getEvents() {
		return events;
	}

	public void setEvents(List<EventSchema> events) {
		this.events = events;
	}

}