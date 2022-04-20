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

import com.internship.retail_management.dto.OperatingFundInsertDTO;
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
	 * @return all operating funds 
	 */
	@GetMapping //método que responde sobre o método Get do HTTP
	public ResponseEntity<List<OperatingFund>> findAll() {
		List<OperatingFund> list = service.findAll(); 
		return ResponseEntity.ok().body(list); //retorna a resposta
	}
	
	/**
	 * Retrieves list of operating funds from a user.
	 * @param id user's id
	 * @return all operating funds from the user
	 */
	@GetMapping(value = "/{userId}") 
	public ResponseEntity<List<OperatingFund>> findByUser(@PathVariable Long userId) {
		List<OperatingFund> list = service.findByUser(userId); 
		return ResponseEntity.ok().body(list); //retorna a resposta
	}
	
	/**
	 * Creates an operating fund from a user.
	 * @param id user's id
	 * @return response of the created operating fund
	 */
	@PostMapping(value = "/{userId}")
	public ResponseEntity<OperatingFund> insert(@PathVariable Long userId, @RequestBody OperatingFundInsertDTO dto) {
		OperatingFund obj = service.insert(userId, dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<OperatingFund> update(@PathVariable Long id, @RequestBody OperatingFundInsertDTO dto) {
		OperatingFund obj = service.update(id, dto);
		return ResponseEntity.ok().body(obj);

	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();

	}
}
