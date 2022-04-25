package com.internship.retail_management.services;

import java.util.IllegalFormatException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

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

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	@Autowired
	private IvaService ivaService;

	@Autowired
	private StockMovementService smService;

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

	public ProductDTO findByName(String name) {
		try {
			Product obj = repository.findByName(name);
			return new ProductDTO(obj);
		} catch (NoSuchElementException e) {
			throw new ResourceNotFoundException(name);
		}
	}

	@Transactional
	public ProductDTO insert(ProductInsertDTO dto) {
		try {
			Product product = repository.findByName(dto.getName());

			if (product != null) {
				throw new ServiceException("This product already exists");
			}

			if (dto.getStock() < 0) {
				throw new StockException("Invalid quantity: " + dto.getStock());
			}

			Product obj = new Product();
			persistData(obj, dto);

			obj.getStockMovements().add(new StockMovement(null, obj.getStock(), Movement.IN, obj));

			obj = repository.save(obj);

			StockMovement stockMovement = obj.getStockMovements().get(0);
			smService.insert(stockMovement);

			return new ProductDTO(obj);
		} catch (IllegalFormatException e) {
			throw new ServiceException("Something went wrong!");
		}

	}

	public ProductDTO update(Long id, ProductUpdateDTO obj) {
		try {
			Product entity = repository.findById(id).get(); // o getOne (deprecated e, por isso, não usado) prepara o
															// objecto pelo JPA (é
															// monitorizado). Desta forma, não há necessidade de ir
															// buscar o objecto à base de dados.
			if (checkName(id, obj)) {
				throw new ServiceException("There is already someone with inserted unique data (name).");
			}

			updateData(entity, obj);

			repository.save(entity);

			return new ProductDTO(entity);
		} catch (NoSuchElementException e) {
			throw new ResourceNotFoundException(id);
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

	@Transactional
	public void updateStock(Long id, Integer stock) {
		Product stockProd = repository.findById(id).get();
		stockProd.setStock(stock);

		StockMovement stockMovement = smService.findAll().get(smService.findAll().size() - 1);
		stockMovement.setProduct(stockProd);

		repository.save(stockProd);
	}

	//auxiliary functions
	public Iva getIva(Integer value) {
		List<Iva> list = ivaService.findAll();

		for (Iva iva : list) {
			if (iva.getValue().getCode() == value) {
				return iva;
			}
		}

		throw new ServiceException("There is no IVA with that value!");

	}

	public Product productFromProductDTO(ProductDTO obj) {
		return repository.findById(obj.getId()).get();
	}

	public void persistData(Product entity, ProductInsertDTO obj) {
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

	public void updateStockDTO(Product entity, ProductDTO obj) {
		entity.setStock(obj.getStock());
	}

	public boolean checkName(Long id, ProductUpdateDTO obj) {
		List<Product> products = repository.findAll();
		// Deve poder alterar o seu próprio nome, por isso, este não pode contar para
		// comparação
		products.removeIf(product -> product.getId() == id);

		for (Product entity : products) {
			if (entity.getName().equals(obj.getName())) {
				return true;
			}
		}

		return false;

	}

}
