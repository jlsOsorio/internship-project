package com.internship.retail_management.entities.enums;

/**
 * Enumeration for the different values of IVA.
 * 
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 */
public enum IvaValues {

	NULL(0), LOW(6), INTERMEDIATE(13), NORMAL(23);

	private int code;

	/**
	 * Sets IVA value.
	 * 
	 * @param code IVA value's code.
	 */
	private IvaValues(int code) {
		this.code = code;
	}

	/**
	 * Retrieves the IVA values's code.
	 * 
	 * @return
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Gets IVA's code and returns value of said IVA.
	 * 
	 * @param code IVA value's code
	 * @return value of the IVA
	 * @throws IllegalArgumentException
	 */
	public static IvaValues valueOf(int code) {
		for (IvaValues value : IvaValues.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}

		throw new IllegalArgumentException("Invalid IVA value!");
	}
}
