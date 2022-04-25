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

import com.internship.retail_management.dto.IvaInsertDTO;
import com.internship.retail_management.entities.Iva;
import com.internship.retail_management.services.IvaService;

/**
 * This class works as a controller for the IVA.
 * 
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 *
 */
@RestController
@RequestMapping(value = "/iva")
public class IvaController {

	@Autowired
	private IvaService service;

	/**
	 * Retrieves IVA list.
	 * 
	 * @return response
	 */
	@GetMapping
	public ResponseEntity<List<Iva>> findAll() {
		List<Iva> list = service.findAll();
		return ResponseEntity.ok().body(list); // retorna a resposta
	}

	/**
	 * Retrieves IVA by id.
	 * 
	 * @param id IVA's id
	 * @return response
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Iva> findById(@PathVariable Long id) {
		Iva obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping
	public ResponseEntity<Iva> insertStock(@RequestBody IvaInsertDTO dto) {
		Iva obj = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);

	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Iva> update(@PathVariable Long id, @RequestBody IvaInsertDTO dto) {
		Iva obj = service.update(id, dto);
		return ResponseEntity.ok().body(obj);

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();

	}

}
