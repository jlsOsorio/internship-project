package com.internship.retail_management.services.exceptions;

public class AuthException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	// 401 error - unauthorized
	public AuthException(String msg) {
		super(msg);
	}

}
