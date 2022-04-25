package com.internship.retail_management.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.internship.retail_management.dto.InvoicedProductDTO;
import com.internship.retail_management.services.InvoicedProductService;

/**
 * This class works as a controller for the invoiced product.
 * 
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 *
 */
@RestController
@RequestMapping(value = "/invoicedproducts")
public class InvoicedProductController {

	@Autowired
	private InvoicedProductService service;

	/**
	 * Retrieves invoiced product list.
	 * 
	 * @return response
	 */
	@GetMapping
	public ResponseEntity<List<InvoicedProductDTO>> findAll() {
		List<InvoicedProductDTO> list = service.findAll();
		return ResponseEntity.ok().body(list); // retorna a resposta
	}

	/**
	 * Retrieves invoiced product by id.
	 * 
	 * @param id invoiced product's id
	 * @return response
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<InvoicedProductDTO> findById(@PathVariable Long id) {
		InvoicedProductDTO obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

}
