package com.internship.retail_management.services;

import java.util.IllegalFormatException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.internship.retail_management.dto.ProductDTO;
import com.internship.retail_management.dto.ProductInsertDTO;
import com.internship.retail_management.entities.Iva;
import com.internship.retail_management.entities.Product;
import com.internship.retail_management.entities.StockMovement;
import com.internship.retail_management.entities.enums.Movement;
import com.internship.retail_management.repositories.ProductRepository;
import com.internship.retail_management.services.exceptions.ResourceNotFoundException;
import com.internship.retail_management.services.exceptions.ServiceException;
import com.internship.retail_management.services.exceptions.StockException;

@Service // regista a classe como componente do Spring para ele conhecer e ser
			// automaticamente injectada (autowired). Existem também o Component e o
			// Repository, para o mesmo fim
public class ProductService {

	@Autowired
	private ProductRepository repository;

	@Autowired
	private IvaService ivaService;
	
	@Autowired
	private StockMovementService smService;

//	@Autowired
//	private ProductRepository smRepository;

	public List<ProductDTO> findAll() {
		List<Product> list = repository.findAll();
		return list.stream().map(prod -> new ProductDTO(prod)).collect(Collectors.toList());

	}

	public ProductDTO findById(Long id) {
		try {
			Optional<Product> obj = repository.findById(id);
			return new ProductDTO(obj.get());
		} catch (NoSuchElementException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	public ProductDTO insert(ProductInsertDTO dto) {
		try {
			if (dto.getStock() < 0) {
				throw new StockException(dto.getStock());
			}

			Product obj = new Product();
			Iva iva = getIva(dto.getIvaValue());
			obj.setName(dto.getName());
			obj.setStock(dto.getStock());
			obj.setIvaValue(iva);
			obj.setGrossPrice(dto.getGrossPrice());
			obj.setTaxedPrice(iva);
			
			obj = repository.save(obj);
			
			obj.getStockMovements().add(new StockMovement(null, obj.getStock(), Movement.IN, obj));
			smService.insert(obj.getStockMovements().get(0));

			return new ProductDTO(obj);
		} catch (IllegalFormatException e) {
			throw new ServiceException("Something went wrong!");
		}

	}

	public Iva getIva(Integer value) {
		List<Iva> list = ivaService.findAll();
		
		for (Iva iva : list) {
			if (iva.getValue().getCode() == value) {
				return iva;
			}
		}
		
			throw new ServiceException("There is no IVA with that value!");
		
	}

}
