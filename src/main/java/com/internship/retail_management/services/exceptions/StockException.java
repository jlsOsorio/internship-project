package com.internship.retail_management.services.exceptions;

public class StockException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public StockException(String msg) {
		super(msg);
	}

}
