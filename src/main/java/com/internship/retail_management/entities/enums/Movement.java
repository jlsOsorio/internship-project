package com.internship.retail_management.entities.enums;

/**
 * Enumeration for the types of movement.
 * 
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 */
public enum Movement {

	IN(0), OUT(1);

	private int code;

	/**
	 * Sets movement.
	 * 
	 * @param code movement's code
	 */
	private Movement(int code) {
		this.code = code;
	}

	/**
	 * Retrieves the movement's code.
	 * 
	 * @return
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Gets movement's code and returns type of said movement.
	 * 
	 * @param code movement type's code
	 * @return value of the movement
	 * @throws IllegalArgumentException
	 */
	public static Movement valueOf(int code) {
		for (Movement value : Movement.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}

		throw new IllegalArgumentException("Invalid Movement value!");
	}
}
