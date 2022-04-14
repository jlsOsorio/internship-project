package com.internship.retail_management.services;

import java.util.IllegalFormatException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.internship.retail_management.dto.ProductDTO;
import com.internship.retail_management.dto.ProductInsertDTO;
import com.internship.retail_management.dto.ProductUpdateDTO;
import com.internship.retail_management.entities.Iva;
import com.internship.retail_management.entities.Product;
import com.internship.retail_management.entities.StockMovement;
import com.internship.retail_management.entities.enums.Movement;
import com.internship.retail_management.repositories.ProductRepository;
import com.internship.retail_management.services.exceptions.DatabaseException;
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
			persistData(obj, dto);

			obj = repository.save(obj);

			obj.getStockMovements().add(new StockMovement(null, obj.getStock(), Movement.IN, obj));
			smService.insert(obj.getStockMovements().get(0));

			return new ProductDTO(obj);
		} catch (IllegalFormatException e) {
			throw new ServiceException("Something went wrong!");
		}

	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}

	}
	
	public ProductDTO update(Long id, ProductUpdateDTO obj) {
		try {
			Product entity = repository.findById(id).get(); // o getOne (deprecated e, por isso, não usado) prepara o objecto pelo JPA (é
															// monitorizado). Desta forma, não há necessidade de ir
															// buscar o objecto à base de dados.


			updateData(entity, obj);

			repository.save(entity);

			return new ProductDTO(entity);
		} catch (NoSuchElementException e) {
			throw new ResourceNotFoundException(id);
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
	
	private void persistData(Product entity, ProductInsertDTO obj) {
		Iva iva = getIva(obj.getIvaValue());
		
		entity.setName(obj.getName());
		entity.setIvaValue(iva);
		entity.setStock(obj.getStock());
		entity.setGrossPrice(obj.getGrossPrice());
		entity.setTaxedPrice(iva);
	}
	
	private void updateData(Product entity, ProductUpdateDTO obj) {
		Iva iva = getIva(obj.getIvaValue());
		
		entity.setName(obj.getName());
		entity.setIvaValue(iva);
		entity.setGrossPrice(obj.getGrossPrice());
		entity.setTaxedPrice(iva);
	}

}
