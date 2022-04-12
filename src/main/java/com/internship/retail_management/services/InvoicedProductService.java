package com.internship.retail_management.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.internship.retail_management.entities.InvoicedProduct;
import com.internship.retail_management.repositories.InvoicedProductRepository;

@Service //regista a classe como componente do Spring para ele conhecer e ser automaticamente injectada (autowired). Existem também o Component e o Repository, para o mesmo fim
public class InvoicedProductService {
	
	@Autowired
	private InvoicedProductRepository repository;
	
	public List<InvoicedProduct> findAll() {
		return repository.findAll();
	}
	
	public InvoicedProduct findById(Long id) {
		Optional<InvoicedProduct> obj = repository.findById(id);
		return obj.get();
	}

}
