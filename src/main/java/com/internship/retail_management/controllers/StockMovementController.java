package com.internship.retail_management.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.internship.retail_management.entities.StockMovement;
import com.internship.retail_management.services.StockMovementService;

/**
 * This class works as a controller for the stock movement.
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 *
 */
@RestController
@RequestMapping(value = "/stockmovements")
public class StockMovementController {
	
	//Injecção de dependência automática
	@Autowired
	private StockMovementService service;
	
	/**
	 * Retrieves stock movement list.
	 * @return response
	 */
	@GetMapping //método que responde sobre o método Get do HTTP
	public ResponseEntity<List<StockMovement>> findAll() {
		List<StockMovement> list = service.findAll(); 
		return ResponseEntity.ok().body(list); //retorna a resposta
	}
	
	/**
	 * Retrieves stock movement by id.
	 * @param id stock movement's id
	 * @return response
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<StockMovement> findById(@PathVariable Long id) {
		StockMovement obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

}
