package com.internship.retail_management.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.internship.retail_management.entities.CashRegister;
import com.internship.retail_management.services.CashRegisterService;

@RestController
@RequestMapping(value = "/cashregisters")
public class CashRegisterController {
	
	//Injecção de dependência automática
	@Autowired
	private CashRegisterService service;
	
	@GetMapping //método que responde sobre o método Get do HTTP
	public ResponseEntity<List<CashRegister>> findAll() {
		List<CashRegister> list = service.findAll(); 
		return ResponseEntity.ok().body(list); //retorna a resposta
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<CashRegister> findById(@PathVariable Long id) {
		CashRegister obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

}
