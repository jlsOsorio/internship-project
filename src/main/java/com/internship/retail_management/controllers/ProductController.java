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

import com.internship.retail_management.dto.ProductDTO;
import com.internship.retail_management.dto.ProductInsertDTO;
import com.internship.retail_management.dto.ProductUpdateDTO;
import com.internship.retail_management.entities.Product;
import com.internship.retail_management.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductController {
	
	//Injecção de dependência automática
	@Autowired
	private ProductService service;
	
	@GetMapping //método que responde sobre o método Get do HTTP
	public ResponseEntity<List<ProductDTO>> findAll() {
		List<ProductDTO> list = service.findAll(); 
		return ResponseEntity.ok().body(list); //retorna a resposta
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
		ProductDTO obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping //RequestBody permite que, no momento da requisição, esta possa ser feita em JSON, e devolver o objecto, desserializando-o
	public ResponseEntity<ProductDTO> insert(@RequestBody ProductInsertDTO dto) {
		ProductDTO obj = service.insert(dto);
		//URI vai servir para que, no momento da criação do objecto em JSON (código 201), exista no cabeçalho uma location com o endereço que retorna esse mesmo objecto.
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		Product newProd = new Product();
		newProd.setId(obj.getId());
		newProd.setName(obj.getName());
		newProd.setStock(obj.getStock());
		newProd.setIvaValue(service.getIva(obj.getIvaValue()));
		newProd.setGrossPrice(obj.getGrossPrice());
		//newProd.setTaxedPrice(service.getIva(obj.getIvaValue()));
//		newProd.getStockMovements().add(new StockMovement(null, newProd.getStock(), Movement.IN, newProd));
//		smService.insert(newProd.getStockMovements().get(0));
		return ResponseEntity.created(uri).body(obj);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();

	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductUpdateDTO dto) {
		ProductDTO obj = service.update(id, dto);
		return ResponseEntity.ok().body(obj);

	}
	
}
