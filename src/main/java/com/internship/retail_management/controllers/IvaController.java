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

/**
 * This class works as a controller for the IVA.
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 *
 */
@RestController
@RequestMapping(value = "/iva")
public class IvaController {
	
	//Injecção de dependência automática
	@Autowired
	private IvaService service;
	
	/**
	 * Retrieves IVA list.
	 * @return response
	 */
	@GetMapping //método que responde sobre o método Get do HTTP
	public ResponseEntity<List<Iva>> findAll() {
		List<Iva> list = service.findAll(); 
		return ResponseEntity.ok().body(list); //retorna a resposta
	}
	
	/**
	 * Retrieves IVA by id.
	 * @param id IVA's id
	 * @return response
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Iva> findById(@PathVariable Long id) {
		Iva obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

}
