package com.internship.retail_management.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.internship.retail_management.entities.CashRegister;
import com.internship.retail_management.repositories.CashRegisterRepository;

@Service //regista a classe como componente do Spring para ele conhecer e ser automaticamente injectada (autowired). Existem também o Component e o Repository, para o mesmo fim
public class CashRegisterService {
	
	@Autowired
	private CashRegisterRepository repository;
	
	public List<CashRegister> findAll() {
		return repository.findAll();
	}
	
	public CashRegister findById(Long id) {
		Optional<CashRegister> obj = repository.findById(id);
		return obj.get();
	}

}
