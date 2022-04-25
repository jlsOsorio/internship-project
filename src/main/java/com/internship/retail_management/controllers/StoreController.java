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

import com.internship.retail_management.dto.StoreInsertDTO;
import com.internship.retail_management.entities.Store;
import com.internship.retail_management.services.StoreService;

/**
 * This class works as a controller for the store.
 * 
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 *
 */
@RestController
@RequestMapping(value = "/stores")
public class StoreController {

	@Autowired
	private StoreService service;

	/**
	 * Retrieves store list.
	 * 
	 * @return response
	 */
	@GetMapping
	public ResponseEntity<List<Store>> findAll() {
		List<Store> list = service.findAll();
		return ResponseEntity.ok().body(list); // retorna a resposta
	}

	/**
	 * Retrieves store by id.
	 * 
	 * @param id store's id
	 * @return response
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Store> findById(@PathVariable Long id) {
		Store obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping
	public ResponseEntity<Store> insert(@RequestBody StoreInsertDTO dto) {
		Store obj = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);

	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Store> update(@PathVariable Long id, @RequestBody StoreInsertDTO dto) {
		Store obj = service.update(id, dto);
		return ResponseEntity.ok().body(obj);

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();

	}
}
