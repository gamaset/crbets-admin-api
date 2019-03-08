package com.gamaset.crbetadmin.repository.entity;

import java.util.Arrays;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum WalletStatusEnum {

	CLOSED("Encerrada"), OPEN("Aberto");
	
	private String description;

	private WalletStatusEnum(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

    public static Optional<WalletStatusEnum> valueOf(int statusId) {
        return Arrays.stream(values())
            .filter(status -> status.ordinal() == statusId)
            .findFirst();
    }
}
