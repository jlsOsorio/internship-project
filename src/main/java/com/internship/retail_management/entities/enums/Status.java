package com.internship.retail_management.entities.enums;

/**
 * Enumeration for the status's values.
 * 
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 */
public enum Status {

	// Numeros para que sejam íntegros ao valor na tabela, caso haja alguma
	// alteração na enumeração
	INACTIVE(0), ACTIVE(1);

	private int code;

	/**
	 * Sets the values for the status.
	 * 
	 * @param code status's code
	 */
	private Status(int code) {
		this.code = code;
	}

	/**
	 * Retrieves the status's code.
	 * 
	 * @return
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Gets status's code and returns value of said status.
	 * 
	 * @param code status values's code
	 * @return value of the status
	 * @throws IllegalArgumentException
	 */
	public static Status valueOf(int code) {
		for (Status value : Status.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}

		throw new IllegalArgumentException("Invalid Status value!");
	}

}
