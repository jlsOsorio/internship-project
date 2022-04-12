package com.internship.retail_management.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.internship.retail_management.entities.Invoice;
import com.internship.retail_management.repositories.InvoiceRepository;

@Service //regista a classe como componente do Spring para ele conhecer e ser automaticamente injectada (autowired). Existem também o Component e o Repository, para o mesmo fim
public class InvoiceService {
	
	@Autowired
	private InvoiceRepository repository;
	
	public List<Invoice> findAll() {
		return repository.findAll();
	}
	
	public Invoice findById(Long id) {
		Optional<Invoice> obj = repository.findById(id);
		return obj.get();
	}

}
