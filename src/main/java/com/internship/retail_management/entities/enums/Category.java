package com.internship.retail_management.entities.enums;

/**
 * Enumeration for the user category.
 * 
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 */
public enum Category {

	// Numeros para que sejam íntegros ao valor na tabela, caso haja alguma
	// alteração na enumeração
	EMPLOYEE(0), SUPERVISOR(1);

	private int code;

	/**
	 * Sets category.
	 * 
	 * @param code category's code.
	 */
	private Category(int code) {
		this.code = code;
	}

	/**
	 * Retrieves the category code.
	 * 
	 * @return
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Gets category's code and returns the value of said category.
	 * 
	 * @param code Category's code
	 * @return value of the category
	 * @throws IllegalArgumentException
	 */
	public static Category valueOf(int code) {
		for (Category value : Category.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}

		throw new IllegalArgumentException("Invalid Category value!");
	}

}
