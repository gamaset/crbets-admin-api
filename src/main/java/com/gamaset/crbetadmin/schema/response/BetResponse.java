package com.gamaset.crbetadmin.schema.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.gamaset.crbetadmin.repository.entity.BetModel;
import com.gamaset.crbetadmin.repository.entity.BetStatusEnum;
import com.gamaset.crbetadmin.repository.entity.CustomerModel;
import com.gamaset.crbetadmin.repository.entity.EventModel;
import com.gamaset.crbetadmin.schema.EventSchema;

@JsonPropertyOrder({"id", "hashId", "totalOdd", "betValue", "profit", "expectedValueDiscountCommission", "commissionPercent", "commissionValue", 
	"status", "createdDate", "updatedDate", "customer", "events"})
public class BetResponse {

	private Long id;
	private String hashId;
	private CustomerModel customer;
	private BigDecimal betValue;
	private BigDecimal expectedValueDiscountCommission;
	private BigDecimal commissionValue;
	private BigDecimal commissionPercent;
	private BigDecimal profit;
	private Double totalOdd;
//	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private BetStatusEnum status;
	private List<EventSchema> events;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime createdDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime updatedDate;

	public BetResponse() {
		// TODO Auto-generated constructor stub
	}

	public BetResponse(BetModel model, List<EventModel> eventsModel) {
		setId(model.getId());
		setHashId(model.getHashId());
		setBetValue(model.getBetValue());
		setExpectedValueDiscountCommission(model.getExpectedValueDiscountCommission());
		setCommissionValue(model.getCommissionValue());
		setCommissionPercent(model.getCommissionPercent());
		setTotalOdd(model.getTotalOdd());
		setStatus(model.getStatus());
		setCustomer(model.getCustomer());
		setProfit(model.getProfitValue());
		setCreatedDate(model.getCreateAt());
		setUpdatedDate(model.getUpdateAt());
		addEvents(eventsModel);
	}

	private void addEvents(List<EventModel> eventsModel) {
		if (CollectionUtils.isEmpty(events)) {
			events = new ArrayList<>();
		}

		if (!CollectionUtils.isEmpty(eventsModel)) {
			eventsModel.stream().forEach(e -> {
				events.add(new EventSchema(e));
			});
		}
	}

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

	public CustomerModel getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerModel customer) {
		this.customer = customer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHashId() {
		return hashId;
	}

	public void setHashId(String hashId) {
		this.hashId = hashId;
	}

	public BigDecimal getBetValue() {
		return betValue;
	}

	public void setBetValue(BigDecimal betValue) {
		this.betValue = betValue;
	}

	public BigDecimal getExpectedValueDiscountCommission() {
		return expectedValueDiscountCommission;
	}

	public void setExpectedValueDiscountCommission(BigDecimal expectedValueDiscountCommission) {
		this.expectedValueDiscountCommission = expectedValueDiscountCommission;
	}

	public BigDecimal getCommissionValue() {
		return commissionValue;
	}

	public void setCommissionValue(BigDecimal commissionValue) {
		this.commissionValue = commissionValue;
	}

	public BigDecimal getCommissionPercent() {
		return commissionPercent;
	}

	public void setCommissionPercent(BigDecimal commissionPercent) {
		this.commissionPercent = commissionPercent;
	}

	public Double getTotalOdd() {
		return totalOdd;
	}

	public void setTotalOdd(Double totalOdd) {
		this.totalOdd = totalOdd;
	}

	public BetStatusEnum getStatus() {
		return status;
	}

	public void setStatus(BetStatusEnum status) {
		this.status = status;
	}

	public List<EventSchema> getEvents() {
		return events;
	}

	public void setEvents(List<EventSchema> events) {
		this.events = events;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

}
