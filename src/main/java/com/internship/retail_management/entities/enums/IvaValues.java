package com.internship.retail_management.entities.enums;

public enum IvaValues {

	NULL(0),
	LOW(6),
	INTERMEDIATE(13),
	NORMAL(23);
	
	private int code;

	private IvaValues(int code) {
			this.code = code;
		}

	public int getCode() {
		return code;
	}

	public static IvaValues valueOf(int code) {
		for (IvaValues value : IvaValues.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}

		throw new IllegalArgumentException("Invalid IVA value!");
	}
}
