package com.internship.retail_management.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.internship.retail_management.entities.InvoicedProduct;
import com.internship.retail_management.repositories.InvoicedProductRepository;
import com.internship.retail_management.services.exceptions.ServiceException;

@Service // regista a classe como componente do Spring para ele conhecer e ser
			// automaticamente injectada (autowired). Existem também o Component e o
			// Repository, para o mesmo fim
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

	public InvoicedProduct insert(InvoicedProduct obj) {
		try {
			if (obj.getQuantity() <= 0) {
				throw new ServiceException("The quantity of the movement must be a positive number.");
			}
			return repository.save(obj);
		} catch (IllegalArgumentException e) {
			throw new ServiceException("Something went wrong!");
		}
	}

//	private void persistInvoice(Invoice obj, InvoicedProductDTO dto) {
//		obj.setDate(Instant.now());
//		obj.setTransaction(dto.getTransaction());
//		obj.setCashRegister(cashRegisterService.findById(dto.getCashRegisterId()));
//		
//		UserDTO user = userService.findById(dto.getUserId());
//		obj.setUser(userService.userFromUserDTO(user));
//	}

}
