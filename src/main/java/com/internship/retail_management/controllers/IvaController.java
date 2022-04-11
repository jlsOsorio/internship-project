package com.internship.retail_management.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.internship.retail_management.entities.Iva;
import com.internship.retail_management.services.IvaService;

@RestController
@RequestMapping(value = "/iva")
public class IvaController {
	
	//Injecção de dependência automática
	@Autowired
	private IvaService service;
	
	@GetMapping //método que responde sobre o método Get do HTTP
	public ResponseEntity<List<Iva>> findAll() {
		List<Iva> list = service.findAll(); 
		return ResponseEntity.ok().body(list); //retorna a resposta
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Iva> findById(@PathVariable Long id) {
		Iva obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

}
