package com.internship.retail_management.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.internship.retail_management.entities.InvoicedProduct;
import com.internship.retail_management.services.InvoicedProductService;

@RestController
@RequestMapping(value = "/invoicedproducts")
public class InvoicedProductController {
	
	//Injecção de dependência automática
	@Autowired
	private InvoicedProductService service;
	
	@GetMapping //método que responde sobre o método Get do HTTP
	public ResponseEntity<List<InvoicedProduct>> findAll() {
		List<InvoicedProduct> list = service.findAll(); 
		return ResponseEntity.ok().body(list); //retorna a resposta
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<InvoicedProduct> findById(@PathVariable Long id) {
		InvoicedProduct obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

}
