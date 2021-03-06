package com.gamaset.crbetadmin.schema.request;

import java.math.BigDecimal;

public class AgentRequest extends UserRequest {

	private BigDecimal budget;
	private BigDecimal percentComission;

	public BigDecimal getBudget() {
		return budget;
	}

	public void setBudget(BigDecimal budget) {
		this.budget = budget;
	}

	public BigDecimal getPercentComission() {
		return percentComission;
	}

	public void setPercentComission(BigDecimal percentComission) {
		this.percentComission = percentComission;
	}

}
