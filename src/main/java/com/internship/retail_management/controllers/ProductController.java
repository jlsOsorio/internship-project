package com.internship.retail_management.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.internship.retail_management.entities.Product;
import com.internship.retail_management.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductController {
	
	//Injecção de dependência automática
	@Autowired
	private ProductService service;
	
	@GetMapping //método que responde sobre o método Get do HTTP
	public ResponseEntity<List<Product>> findAll() {
		List<Product> list = service.findAll(); 
		return ResponseEntity.ok().body(list); //retorna a resposta
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Product> findById(@PathVariable Long id) {
		Product obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

}
