package com.internship.retail_management.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.internship.retail_management.entities.User;
import com.internship.retail_management.repositories.UserRepository;
import com.internship.retail_management.services.exceptions.ServiceException;

@Service //regista a classe como componente do Spring para ele conhecer e ser automaticamente injectada (autowired). Existem também o Component e o Repository, para o mesmo fim
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public List<User> findAll() {
		return repository.findAll();
	}
	
	public User findById(Long id) {
		Optional<User> obj = repository.findById(id);
		return obj.get();
	}
	
	public User insert(@RequestBody User obj) {
		User user = repository.findByEmail(obj.getEmail());
		if (user != null)
		{
			throw new ServiceException("Email já existe!");
		}
		
		obj.setPassword(passwordEncoder.encode(obj.getPassword()));
		
		return repository.save(obj);
	}

}
