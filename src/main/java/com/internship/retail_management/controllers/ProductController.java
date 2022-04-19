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

import com.internship.retail_management.entities.Product;
import com.internship.retail_management.entities.StockMovement;
import com.internship.retail_management.entities.enums.Movement;
import com.internship.retail_management.services.ProductService;
import com.internship.retail_management.services.StockMovementService;

/**
 * This class works as a controller for the product.
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 *
 */
@RestController
@RequestMapping(value = "/products")
public class ProductController {
	
	//Injecção de dependência automática
	@Autowired
	private ProductService service;
	
	@Autowired
	private StockMovementService smService;
	
	/**
	 * Retrieves product list.
	 * @return response
	 */
	@GetMapping //método que responde sobre o método Get do HTTP
	public ResponseEntity<List<Product>> findAll() {
		List<Product> list = service.findAll(); 
		return ResponseEntity.ok().body(list); //retorna a resposta
	}
	
	/**
	 * Retrieves product by id.
	 * @param id product's id
	 * @return response
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Product> findById(@PathVariable Long id) {
		Product obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	/**
	 * Inserts a new product in the list.
	 * @param obj product
	 * @return response
	 */
	@PostMapping //RequestBody permite que, no momento da requisição, esta possa ser feita em JSON, e devolver o objecto, desserializando-o
	public ResponseEntity<Product> insert(@RequestBody Product obj) {
		obj = service.insert(obj);
		//URI vai servir para que, no momento da criação do objecto em JSON (código 201), exista no cabeçalho uma location com o endereço que retorna esse mesmo objecto.
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		obj.getStockMovements().add(new StockMovement(null, obj.getStock(), Movement.IN, obj));
		smService.insert(obj.getStockMovements().get(0));
		return ResponseEntity.created(uri).body(obj);
	}
	
}
