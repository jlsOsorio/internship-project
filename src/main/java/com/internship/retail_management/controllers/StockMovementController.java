package com.internship.retail_management.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.internship.retail_management.dto.StockMovementInsertDTO;
import com.internship.retail_management.entities.StockMovement;
import com.internship.retail_management.services.StockMovementService;

/**
 * This class works as a controller for the stock movement.
 * 
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 *
 */
@RestController
@RequestMapping(value = "/stockmovements")
public class StockMovementController {

	@Autowired
	private StockMovementService service;

	/**
	 * Retrieves stock movement list.
	 * 
	 * @return response
	 */
	@GetMapping
	public ResponseEntity<List<StockMovement>> findAll() {
		List<StockMovement> list = service.findAll();
		return ResponseEntity.ok().body(list); // retorna a resposta
	}

	/**
	 * Retrieves stock movements from products.
	 * 
	 * @param id product's id
	 * @return list of movements from a product
	 */
	@GetMapping(value = "/{productId}")
	public ResponseEntity<List<StockMovement>> findByProduct(@PathVariable Long productId) {
		List<StockMovement> list = service.findByProduct(productId);
		return ResponseEntity.ok().body(list);
	}

	@PostMapping(value = "/{productId}")
	public ResponseEntity<StockMovement> insertStock(@PathVariable Long productId,
			@RequestBody StockMovementInsertDTO dto) {
		StockMovement obj = service.insertStock(productId, dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();

	}
}
