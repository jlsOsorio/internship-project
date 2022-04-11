package com.internship.retail_management.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.internship.retail_management.entities.User;
import com.internship.retail_management.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {
	
	//Injecção de dependência automática
	@Autowired
	private UserService service;
	
	@GetMapping //método que responde sobre o método Get do HTTP
	public ResponseEntity<List<User>> findAll() {
		List<User> list = service.findAll(); 
		return ResponseEntity.ok().body(list); //retorna a resposta
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		User obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

}
