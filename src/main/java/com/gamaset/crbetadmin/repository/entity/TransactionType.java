package com.gamaset.crbetadmin.repository.entity;

import java.util.Arrays;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TransactionType {

	CREDIT_BUDGET("Crédito de Orçamento"), DEBIT_BUDGET("Débito de Orçamento"), 
	CREDIT_AMOUNT_PAID("Crédito de Valor Pago"), DEBIT_AMOUNT_PAID("Débito de Valor Pago"),
	CREDIT_AMOUNT_RECEIVED("Crédito de Valor Recebido"), DEBIT_AMOUNT_RECEIVED("Débito de Valor Recebido"),
	CREDIT_AMOUNT_COMMISION("Crédito de Comissão"), DEBIT_AMOUNT_COMMISION("Débito de Comissão"),
	;
	
	private String description;

	private TransactionType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

    public static Optional<TransactionType> valueOf(int statusId) {
        return Arrays.stream(values())
            .filter(status -> status.ordinal() == statusId)
            .findFirst();
    }
}
