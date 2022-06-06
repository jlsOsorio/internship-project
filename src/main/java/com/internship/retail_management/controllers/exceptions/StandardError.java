package com.internship.retail_management.controllers.exceptions;

import java.io.Serializable;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Standard error class.
 * 
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/**
 * Standard error constructor
 * 
 * @param timestamp error's time stamp
 * @param status    error's status
 * @param error     error's header
 * @param message   error's message
 * @param path      error's path
 */
public class StandardError implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	private Instant timestamp;
	private Integer status;
	private String error;
	private String message;
	private String path;

}
