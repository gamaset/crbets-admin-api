package com.gamaset.crbetadmin.repository.entity;

import java.util.Arrays;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonFormat;


@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BetStatusEnum {

	PENDING("PENDENTE"), REGISTERING("REGISTRADA"), CANCELLED("CANCELADA"), WON("VENCIDA"), LOSE("PERDIDA");

	private int id;
	private String description;

	private BetStatusEnum(String description) {
		this.description = description;
	}

	public int getId() {
		return this.ordinal();
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
