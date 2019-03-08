package com.gamaset.crbetadmin.repository.entity;

import java.util.Arrays;
import java.util.Optional;

public enum BetStatusEnum {

	PENDING("PENDENTE"), REGISTERING("REGISTRADA"), CANCELLED("CANCELADA"), WON("VENCIDA"), LOSE("PERDIDA");

	private String description;

	private BetStatusEnum(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

    public static Optional<BetStatusEnum> valueOf(int statusId) {
        return Arrays.stream(values())
            .filter(status -> status.ordinal() == statusId)
            .findFirst();
    }
}
