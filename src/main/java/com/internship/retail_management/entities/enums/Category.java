package com.internship.retail_management.entities.enums;

public enum Category {

	// Numeros para que sejam íntegros ao valor na tabela, caso haja alguma
	// alteração na enumeração
	EMPLOYEE(0), SUPERVISOR(1);

	private int code;

	private Category(int code) {
			this.code = code;
		}

	public int getCode() {
		return code;
	}

	public static Category valueOf(int code) {
		for (Category value : Category.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}

		throw new IllegalArgumentException("Invalid Category value!");
	}

}
