package com.internship.retail_management.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.internship.retail_management.dto.InvoiceDTO;
import com.internship.retail_management.dto.InvoiceInsertDTO;
import com.internship.retail_management.dto.InvoiceUpdateDTO;
import com.internship.retail_management.services.InvoiceService;

/**
 * This class works as a controller for the invoice.
 * 
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 *
 */
@RestController
@RequestMapping(value = "/invoices")
public class InvoiceController {

	@Autowired
	private InvoiceService service;

	/**
	 * Retrieves invoice list.
	 * 
	 * @return response
	 */
	@GetMapping
	public ResponseEntity<List<InvoiceDTO>> findAll() {
		List<InvoiceDTO> list = service.findAll();
		return ResponseEntity.ok().body(list); // retorna a resposta
	}

	/**
	 * Retrieves invoice by id.
	 * 
	 * @param id invoice's id
	 * @return response
	 */
	@GetMapping(value = "/{invoiceNumber}")
	public ResponseEntity<InvoiceDTO> findById(@PathVariable Long invoiceNumber) {
		InvoiceDTO obj = service.findById(invoiceNumber);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping
	public ResponseEntity<InvoiceDTO> insert(@RequestBody InvoiceInsertDTO dto) {
		InvoiceDTO obj = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getInvoiceNumber())
				.toUri();
		return ResponseEntity.created(uri).body(obj);

	}

	@PutMapping(value = "/{invoiceNumber}")
	public ResponseEntity<InvoiceDTO> update(@PathVariable Long invoiceNumber, @RequestBody InvoiceUpdateDTO dto) {
		InvoiceDTO obj = service.update(invoiceNumber, dto);
		return ResponseEntity.ok().body(obj);

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();

	}

}
