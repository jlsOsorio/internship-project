package com.internship.retail_management.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.internship.retail_management.dto.InvoicedProductDTO;
import com.internship.retail_management.entities.InvoicedProduct;
import com.internship.retail_management.repositories.InvoicedProductRepository;
import com.internship.retail_management.services.exceptions.ResourceNotFoundException;
import com.internship.retail_management.services.exceptions.ServiceException;
import com.internship.retail_management.services.exceptions.StockException;

@Service
public class InvoicedProductService {

	@Autowired
	private InvoicedProductRepository repository;

	@Autowired
	private StockMovementService stockMovementService;

	public List<InvoicedProductDTO> findAll() {
		List<InvoicedProduct> list = repository.findAll();
		return list.stream().map(invoicedProduct -> new InvoicedProductDTO(invoicedProduct))
				.collect(Collectors.toList());
	}

	public InvoicedProductDTO findById(Long id) {
		try {
			Optional<InvoicedProduct> obj = repository.findById(id);
			return new InvoicedProductDTO(obj.get());
		} catch (NoSuchElementException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	public void insert(InvoicedProduct obj) {
		try {
			if (obj.getQuantity() <= 0) {
				throw new StockException("Invalid quantity: " + obj.getQuantity());
			}
			obj.getProduct().getInvoicedProducts().add(obj);
			stockMovementService.insertInvoicedProduct(obj);

			repository.save(obj);
		} catch (IllegalArgumentException e) {
			throw new ServiceException("Something went wrong!");
		}
	}

}
