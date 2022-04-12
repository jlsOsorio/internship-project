package com.internship.retail_management.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.internship.retail_management.entities.StockMovement;
import com.internship.retail_management.repositories.StockMovementRepository;

@Service //regista a classe como componente do Spring para ele conhecer e ser automaticamente injectada (autowired). Existem também o Component e o Repository, para o mesmo fim
public class StockMovementService {
	
	@Autowired
	private StockMovementRepository repository;
	
	public List<StockMovement> findAll() {
		return repository.findAll();
	}
	
	public StockMovement findById(Long id) {
		Optional<StockMovement> obj = repository.findById(id);
		return obj.get();
	}
	
	public StockMovement insert(StockMovement obj) {
		return repository.save(obj);
	}

}
