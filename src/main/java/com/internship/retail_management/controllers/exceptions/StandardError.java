package com.internship.retail_management.controllers.exceptions;

import java.io.Serializable;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Standard error class.
 * 
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 *
 */
public class StandardError implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	private Instant timestamp;
	private Integer status;
	private String error;
	private String message;
	private String path;

	public StandardError() {
	}

	/**
	 * Standard error constructor
	 * 
	 * @param timestamp error's time stamp
	 * @param status    error's status
	 * @param error     error's header
	 * @param message   error's message
	 * @param path      error's path
	 */
	public StandardError(Instant timestamp, Integer status, String error, String message, String path) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.message = message;
		this.path = path;
	}

	/**
	 * Getter for the error's time stamp.
	 * 
	 * @return
	 */
	public Instant getTimestamp() {
		return timestamp;
	}

	/**
	 * Setter for the error's time stamp.
	 * 
	 * @param timestamp error's time stamp
	 */
	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Getter for the error's status.
	 * 
	 * @return
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * Setter for the error's status.
	 * 
	 * @param status error's status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * Getter for the error's header.
	 * 
	 * @return
	 */
	public String getError() {
		return error;
	}

	/**
	 * Setter for the error's header.
	 * 
	 * @param error error's header
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * Getter for the error's message.
	 * 
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setter for the error's message.
	 * 
	 * @param message error's message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Getter for the error's path.
	 * 
	 * @return
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Setter for the error's path.
	 * 
	 * @param path error's path
	 */
	public void setPath(String path) {
		this.path = path;
	}
}
