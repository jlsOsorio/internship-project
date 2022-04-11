package com.internship.retail_management.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.internship.retail_management.entities.Store;
import com.internship.retail_management.repositories.StoreRepository;

@Service //regista a classe como componente do Spring para ele conhecer e ser automaticamente injectada (autowired). Existem também o Component e o Repository, para o mesmo fim
public class StoreService {
	
	@Autowired
	private StoreRepository repository;
	
	public List<Store> findAll() {
		return repository.findAll();
	}
	
	public Store findById(Long id) {
		Optional<Store> obj = repository.findById(id);
		return obj.get();
	}

}
