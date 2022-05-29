package com.internship.retail_management.controllers.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.internship.retail_management.services.exceptions.DatabaseException;
import com.internship.retail_management.services.exceptions.DateException;
import com.internship.retail_management.services.exceptions.ResourceNotFoundException;
import com.internship.retail_management.services.exceptions.ServiceException;
import com.internship.retail_management.services.exceptions.StockException;

/**
 * This class handles the resource exceptions.
 * 
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 *
 */
@ControllerAdvice // Intersecta as excepções que acontecerem, para que possamos tratar, deste
					// lado, essas mesmas excepções
public class ResourceExceptionHandler {

	/**
	 * Resource exception handler.
	 * 
	 * @param e       resource not found exception
	 * @param request type of request
	 * @return error body
	 */
	// Intersecta excepção específica, para cair neste método
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		String error = "Resource not found";
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	/**
	 * Database exception handler.
	 * 
	 * @param e       database exception
	 * @param request type of request
	 * @return error body
	 */
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request) {
		String error = "Database error";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	/**
	 * Service exception handler.
	 * 
	 * @param e       service exception
	 * @param request type of request
	 * @return error body
	 */
	@ExceptionHandler(ServiceException.class)
	public ResponseEntity<StandardError> serviceException(ServiceException e, HttpServletRequest request) {
		String error = "Insert error";
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	/**
	 * Database stock exception handler.
	 * 
	 * @param e       stock exception
	 * @param request type of request
	 * @return error body
	 */
	@ExceptionHandler(StockException.class)
	public ResponseEntity<StandardError> database(StockException e, HttpServletRequest request) {
		String error = "Stock error";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	/**
	 * Date exception handler.
	 * 
	 * @param e       date exception
	 * @param request type of request
	 * @return error body
	 */
	@ExceptionHandler(DateException.class)
	public ResponseEntity<StandardError> database(DateException e, HttpServletRequest request) {
		String error = "Date error";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
}
