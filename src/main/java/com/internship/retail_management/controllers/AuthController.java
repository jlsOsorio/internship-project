package com.internship.retail_management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.internship.retail_management.dto.UserAuthDTO;
import com.internship.retail_management.dto.UserLoginDTO;
import com.internship.retail_management.services.UserService;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
	
	@Autowired
	private UserService service;
	
	/**
	 * Login user.
	 * 
	 * @param obj user
	 * @return response
	 */
	@PostMapping(value = "/login")
	public ResponseEntity<UserAuthDTO> login(@RequestBody UserLoginDTO dto) {
		UserAuthDTO obj = service.login(dto);
		return ResponseEntity.ok().body(obj);

	}
}
