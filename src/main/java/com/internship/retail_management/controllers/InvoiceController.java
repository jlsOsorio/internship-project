package com.internship.retail_management.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.internship.retail_management.dto.InvoiceInsertDTO;
import com.internship.retail_management.entities.Invoice;
import com.internship.retail_management.services.InvoiceService;

/**
 * This class works as a controller for the invoice.
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 *
 */
@RestController
@RequestMapping(value = "/invoices")
public class InvoiceController {
	
	//Injecção de dependência automática
	@Autowired
	private InvoiceService service;
	
	/**
	 * Retrieves invoice list.
	 * @return response
	 */
	@GetMapping //método que responde sobre o método Get do HTTP
	public ResponseEntity<List<Invoice>> findAll() {
		List<Invoice> list = service.findAll(); 
		return ResponseEntity.ok().body(list); //retorna a resposta
	}
	
	/**
	 * Retrieves invoice by id.
	 * @param id invoice's id
	 * @return response
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Invoice> findById(@PathVariable Long id) {
		Invoice obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping
	public ResponseEntity<Invoice> insert(@RequestBody InvoiceInsertDTO dto) {
		Invoice obj = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getInvoiceNumber()).toUri();
		return ResponseEntity.created(uri).body(obj);

	}
}
