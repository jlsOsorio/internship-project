package com.internship.retail_management.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.internship.retail_management.entities.Store;
import com.internship.retail_management.services.StoreService;

/**
 * This class works as a controller for the store.
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 *
 */
@RestController
@RequestMapping(value = "/stores")
public class StoreController {
	
	//Injecção de dependência automática
	@Autowired
	private StoreService service;
	
	/**
	 * Retrieves store list.
	 * @return response
	 */
	@GetMapping //método que responde sobre o método Get do HTTP
	public ResponseEntity<List<Store>> findAll() {
		List<Store> list = service.findAll(); 
		return ResponseEntity.ok().body(list); //retorna a resposta
	}
	
	/**
	 * Retrieves store by id.
	 * @param id store's id
	 * @return response
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Store> findById(@PathVariable Long id) {
		Store obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

}
