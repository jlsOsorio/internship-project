package com.internship.retail_management.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.internship.retail_management.entities.OperatingFund;
import com.internship.retail_management.repositories.OperatingFundRepository;

@Service //regista a classe como componente do Spring para ele conhecer e ser automaticamente injectada (autowired). Existem também o Component e o Repository, para o mesmo fim
public class OperatingFundService {
	
	@Autowired
	private OperatingFundRepository repository;
	
	public List<OperatingFund> findAll() {
		return repository.findAll();
	}
	
	public OperatingFund findById(Long id) {
		Optional<OperatingFund> obj = repository.findById(id);
		return obj.get();
	}

}
