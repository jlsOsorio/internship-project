package com.internship.retail_management.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.internship.retail_management.entities.Invoice;
import com.internship.retail_management.services.InvoiceService;

@RestController
@RequestMapping(value = "/invoices")
public class InvoiceController {
	
	//Injecção de dependência automática
	@Autowired
	private InvoiceService service;
	
	@GetMapping //método que responde sobre o método Get do HTTP
	public ResponseEntity<List<Invoice>> findAll() {
		List<Invoice> list = service.findAll(); 
		return ResponseEntity.ok().body(list); //retorna a resposta
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Invoice> findById(@PathVariable Long id) {
		Invoice obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

}