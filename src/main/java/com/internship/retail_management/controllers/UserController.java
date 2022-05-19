package com.internship.retail_management.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.internship.retail_management.dto.ChangePasswordDTO;
import com.internship.retail_management.dto.UserDTO;
import com.internship.retail_management.dto.UserInsertDTO;
import com.internship.retail_management.dto.UserUpdateDTO;
import com.internship.retail_management.services.UserService;

/**
 * This class works as a controller for the user.
 * 
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 *
 */
@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private UserService service;

	/**
	 * Retrieves user list.
	 * 
	 * @return response
	 */
	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll() {
		List<UserDTO> list = service.findAll();
		return ResponseEntity.ok().body(list); // retorna a resposta
	}

	/**
	 * Retrieves user by id.
	 * 
	 * @param id user's id
	 * @return response
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
		UserDTO obj = service.findById(id);
		return ResponseEntity.ok().body(obj);

	}

	/**
	 * Inserts a new user in the list.
	 * 
	 * @param obj user
	 * @return response
	 */
	@PostMapping
	public ResponseEntity<UserDTO> insert(@RequestBody UserInsertDTO dto) {
		UserDTO obj = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);

	}

	/**
	 * Edit a user with his id.
	 * 
	 * @param id  user's id
	 * @param dto
	 * @return
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserUpdateDTO dto) {
		UserDTO obj = service.update(id, dto);
		return ResponseEntity.ok().body(obj);

	}
	
	@PatchMapping(value = "/{id}")
	public ResponseEntity<UserDTO> changePassword(@PathVariable Long id, @RequestBody ChangePasswordDTO dto) {
		UserDTO obj = service.changePassword(id, dto);
		return ResponseEntity.ok().body(obj);
	}

	/**
	 * Deletes a user from the list using his id.
	 * 
	 * @param id user's id
	 * @return
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();

	}
}
