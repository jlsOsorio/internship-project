package com.internship.retail_management.services.exceptions;

public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	//404 error
	public ResourceNotFoundException(Object attr) {
		super("Resource not found. Id " + attr);
	}

}
