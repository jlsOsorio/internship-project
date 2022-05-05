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

import com.internship.retail_management.dto.ChangePasswordDTO;
import com.internship.retail_management.dto.UserDTO;
import com.internship.retail_management.dto.UserInsertDTO;
import com.internship.retail_management.entities.User;
import com.internship.retail_management.repositories.UserRepository;
import com.internship.retail_management.services.exceptions.DatabaseException;
import com.internship.retail_management.services.exceptions.ResourceNotFoundException;
import com.internship.retail_management.services.exceptions.ServiceException;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	StoreService storeService;

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

	public UserDTO insert(UserInsertDTO dto) {
		try {
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

			persistData(obj, dto);

			obj = repository.save(obj);

			return new UserDTO(obj);
		} catch (IllegalArgumentException e) {
			throw new ServiceException("Something went wrong!");
		}

	}

	public UserDTO update(Long id, UserInsertDTO obj) {
		try {
			User entity = repository.findById(id).get();

			if (checkEmailNif(id, obj)) {
				throw new ServiceException("There is already someone with inserted unique data (email or nif).");
			}

			persistData(entity, obj);

			repository.save(entity);

			return new UserDTO(entity);
		} catch (NoSuchElementException e) {
			throw new ResourceNotFoundException(id);
		}
	}
	
	public UserDTO changePassword(Long id, ChangePasswordDTO obj) {
		User entity = repository.findById(id).get();

		persistPassword(entity, obj);
		
		repository.save(entity);
		
		return new UserDTO(entity);
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

	//auxiliary functions
	public User userFromUserDTO(UserDTO obj) {
		return repository.findById(obj.getId()).get();
	}

	private void persistData(User entity, UserInsertDTO obj) {
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());
		entity.setPhone(obj.getPhone());
		entity.setBirthDate(obj.getBirthDate());
		entity.setNif(obj.getNif());
		entity.setCategory(obj.getCategory());
		entity.setStatus(obj.getStatus());
		entity.setAddress(obj.getAddress());
		entity.setCouncil(obj.getCouncil());
		entity.setZipCode(obj.getZipCode());
		entity.setStore(storeService.findById(obj.getStoreId()));
	}
	
	private void persistPassword(User entity, ChangePasswordDTO obj) {
		entity.setPassword(passwordEncoder.encode(obj.getPassword()));
	}

	public boolean checkEmailNif(Long id, UserInsertDTO obj) {
		List<User> users = repository.findAll();
		// Deve poder alterar o seu próprio email ou nif, por isso, estes não podem
		// contar para comparação
		users.removeIf(user -> user.getId() == id);

		for (User entity : users) {
			if (entity.getEmail().equals(obj.getEmail()) || entity.getNif() == obj.getNif()) {
				return true;
			}
		}

		return false;

	}
}
