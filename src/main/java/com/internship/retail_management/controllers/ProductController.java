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

@RestController
@RequestMapping(value = "/products")
public class ProductController {
	
	//Injecção de dependência automática
	@Autowired
	private ProductService service;
	
	@Autowired
	private StockMovementService smService;
	
	@GetMapping //método que responde sobre o método Get do HTTP
	public ResponseEntity<List<Product>> findAll() {
		List<Product> list = service.findAll(); 
		return ResponseEntity.ok().body(list); //retorna a resposta
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Product> findById(@PathVariable Long id) {
		Product obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
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
