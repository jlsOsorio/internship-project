package com.internship.retail_management.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.internship.retail_management.dto.UserDTO;
import com.internship.retail_management.dto.UserInsertDTO;
import com.internship.retail_management.entities.User;
import com.internship.retail_management.repositories.UserRepository;
import com.internship.retail_management.services.exceptions.DatabaseException;
import com.internship.retail_management.services.exceptions.ResourceNotFoundException;
import com.internship.retail_management.services.exceptions.ServiceException;

@Service // regista a classe como componente do Spring para ele conhecer e ser
			// automaticamente injectada (autowired). Existem também o Component e o
			// Repository, para o mesmo fim
public class UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public List<UserDTO> findAll() {
		List<User> users = repository.findAll();
		return users.stream().map(user -> new UserDTO(user)).collect(Collectors.toList());
	}

	public UserDTO findById(Long id) {
		try {
			Optional<User> obj = repository.findById(id);
			return new UserDTO(obj.get());
		} catch (NoSuchElementException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	public UserDTO insert(@RequestBody UserInsertDTO dto) {
		try
		{
			User user = repository.findByEmail(dto.getEmail());
			if (user != null) {
				throw new ServiceException("Email already exists!");
			}

			user = repository.findByNif(dto.getNif());
			if (user != null) {
				throw new ServiceException("Nif already exists!");
			}

			// Instancia um novo user, porque na base de dados está um utilizador, e não o
			// DTO
			User obj = new User();

			obj.setName(dto.getName());
			obj.setEmail(dto.getEmail());
			obj.setPassword(passwordEncoder.encode(dto.getPassword()));
			obj.setBirthDate(dto.getBirthDate());
			obj.setNif(dto.getNif());
			obj.setCategory(dto.getCategory());
			obj.setStatus(dto.getStatus());
			obj.setAddress(dto.getAddress());
			obj.setCouncil(dto.getCouncil());
			obj.setCouncil(dto.getCouncil());
			obj.setZipCode(dto.getZipCode());
			obj.setStore(dto.getStore());

			obj = repository.save(obj);

			return new UserDTO(obj);
		}
		catch (Exception e)
		{
			throw new ServiceException("Something went wrong!");
		}
		

	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}

	}

	public UserDTO update(Long id, UserInsertDTO obj) {
		try {
			User entity = repository.findById(id).get(); // o getOne (deprecated) prepara o objecto pelo JPA (é
															// monitorizado). Desta forma, não há necessidade de ir
															// buscar o objecto à base de dados.

			if (checkEmailNif(obj)) {
				throw new ServiceException("There is already someone with inserted unique data (email or nif).");
			}

			updateData(entity, obj);

			repository.save(entity);

			return new UserDTO(entity);
		} catch (NoSuchElementException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(User entity, UserInsertDTO obj) {
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());
		entity.setPassword(obj.getPassword());
		entity.setPhone(obj.getPhone());
		entity.setBirthDate(obj.getBirthDate());
		entity.setNif(obj.getNif());
		entity.setCategory(obj.getCategory());
		entity.setStatus(obj.getStatus());
		entity.setAddress(obj.getAddress());
		entity.setCouncil(obj.getCouncil());
		entity.setZipCode(obj.getZipCode());
		entity.setStore(obj.getStore());
	}

	public boolean checkEmailNif(UserInsertDTO obj) {
		List<User> users = repository.findAll();
		
		for (User entity : users)
		{
			if (entity.getEmail().equals(obj.getEmail()) || entity.getNif() == obj.getNif()) {
				return true;
			}
		}

		return false;

	}
}
