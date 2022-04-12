package com.internship.retail_management.entities.enums;

public enum TransactionType {

	// Numeros para que sejam íntegros ao valor na tabela, caso haja alguma
	// alteração na enumeração
	DEBIT(0), CREDIT(1);

	private int code;

	private TransactionType(int code) {
			this.code = code;
		}

	public int getCode() {
		return code;
	}

	public static TransactionType valueOf(int code) {
		for (TransactionType value : TransactionType.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}

		throw new IllegalArgumentException("Invalid transaction type value!");
	}

}
