package com.internship.retail_management.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.internship.retail_management.entities.CashRegister;
import com.internship.retail_management.entities.Store;
import com.internship.retail_management.repositories.CashRegisterRepository;
import com.internship.retail_management.services.exceptions.DatabaseException;
import com.internship.retail_management.services.exceptions.ResourceNotFoundException;

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
	
	public List<CashRegister> findByStore(Store store) {
		return repository.findByStore(store);
	}
	
	public CashRegister insert(CashRegister obj) {
		return repository.save(obj);
	}
	
	public void delete(Long id) {
		try
		{
			repository.deleteById(id);	
		}
		catch (EmptyResultDataAccessException e)
		{
			throw new ResourceNotFoundException(id);
		}
		catch (DataIntegrityViolationException e)
		{
			throw new DatabaseException(e.getMessage());
		}
	}

}
