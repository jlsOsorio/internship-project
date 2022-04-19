package com.internship.retail_management.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.internship.retail_management.entities.OperatingFund;
import com.internship.retail_management.services.OperatingFundService;

/**
 * This class works as a controller for the operating fund.
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 *
 */
@RestController
@RequestMapping(value = "/operatingfunds")
public class OperatingFundController {
	
	//Injecção de dependência automática
	@Autowired
	private OperatingFundService service;
	
	/**
	 * Retrieves operating fund list.
	 * @return response
	 */
	@GetMapping //método que responde sobre o método Get do HTTP
	public ResponseEntity<List<OperatingFund>> findAll() {
		List<OperatingFund> list = service.findAll(); 
		return ResponseEntity.ok().body(list); //retorna a resposta
	}
	
	/**
	 * Retrieves operating fund id.
	 * @param id operating fund's id
	 * @return response
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<OperatingFund> findById(@PathVariable Long id) {
		OperatingFund obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

}
