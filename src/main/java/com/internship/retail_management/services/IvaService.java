package com.internship.retail_management.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.internship.retail_management.entities.Iva;
import com.internship.retail_management.repositories.IvaRepository;

@Service //regista a classe como componente do Spring para ele conhecer e ser automaticamente injectada (autowired). Existem também o Component e o Repository, para o mesmo fim
public class IvaService {
	
	@Autowired
	private IvaRepository repository;
	
	public List<Iva> findAll() {
		return repository.findAll();
	}
	
	public Iva findById(Long id) {
		Optional<Iva> obj = repository.findById(id);
		return obj.get();
	}

}
