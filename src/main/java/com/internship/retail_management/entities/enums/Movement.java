package com.internship.retail_management.entities.enums;

public enum Movement {

	IN(0), OUT(1);
	
	private int code;

	private Movement(int code) {
			this.code = code;
		}

	public int getCode() {
		return code;
	}

	public static Movement valueOf(int code) {
		for (Movement value : Movement.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}

		throw new IllegalArgumentException("Invalid Movement value!");
	}
}
