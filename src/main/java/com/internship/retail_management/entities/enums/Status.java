package com.internship.retail_management.entities.enums;

public enum Status {

	// Numeros para que sejam íntegros ao valor na tabela, caso haja alguma
	// alteração na enumeração
	INACTIVE(0), ACTIVE(1);

	private int code;

	private Status(int code) {
			this.code = code;
		}

	public int getCode() {
		return code;
	}

	public static Status valueOf(int code) {
		for (Status value : Status.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}

		throw new IllegalArgumentException("Invalid StoreStatus code!");
	}

}
