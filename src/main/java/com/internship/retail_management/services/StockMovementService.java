package com.internship.retail_management.services;

import java.util.IllegalFormatException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.internship.retail_management.dto.ProductDTO;
import com.internship.retail_management.dto.StockMovementInsertDTO;
import com.internship.retail_management.entities.Product;
import com.internship.retail_management.entities.StockMovement;
import com.internship.retail_management.entities.enums.Movement;
import com.internship.retail_management.repositories.StockMovementRepository;
import com.internship.retail_management.services.exceptions.DatabaseException;
import com.internship.retail_management.services.exceptions.ResourceNotFoundException;
import com.internship.retail_management.services.exceptions.ServiceException;

@Service // regista a classe como componente do Spring para ele conhecer e ser
			// automaticamente injectada (autowired). Existem também o Component e o
			// Repository, para o mesmo fim
public class StockMovementService {

	@Autowired
	private StockMovementRepository repository;

	@Autowired
	private ProductService productService;

	public List<StockMovement> findAll() {
		return repository.findAll();
	}

	public StockMovement findById(Long id) {
		try {
			Optional<StockMovement> obj = repository.findById(id);
			return obj.get();
		} catch (NoSuchElementException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	public List<StockMovement> findByProduct(Long productId) {
		try {
			ProductDTO productDTO = productService.findById(productId);
			Product product = productService.productFromProductDTO(productDTO);
			return repository.findByProduct(product);
		} catch (NoSuchElementException e) {
			throw new ResourceNotFoundException(productId);
		}
	}

	public StockMovement insert(StockMovement obj) {
		try {

			return repository.save(obj);
		} catch (IllegalFormatException e) {
			throw new ServiceException("Something went wrong!");
		}

	}

	public StockMovement insertStock(Long productId, StockMovementInsertDTO dto) {
		try {

			StockMovement obj = new StockMovement();
			persistData(obj, dto);

			ProductDTO stockProdDTO = productService.findById(productId);

			Integer newStock = stockProdDTO.getStock();

			if (obj.getQuantity() <= 0) {
				throw new ServiceException("The quantity of the movement must be a positive number.");
			}

			if (obj.getMovement() == Movement.IN) {
				newStock += obj.getQuantity();
			}

			if (obj.getMovement() == Movement.OUT) {
				newStock -= obj.getQuantity();
			}

			StockMovement stockMovement = repository.save(obj);

			productService.updateStock(productId, newStock);

			return stockMovement;
		} catch (IllegalFormatException e) {
			throw new ServiceException("Something went wrong!");
		}

	}

//	public StockMovement update(Long id, StockMovementInsertDTO obj) {
//		try {
//			StockMovement entity = repository.findById(id).get(); // o getOne (deprecated e, por isso, não usado)
//																	// prepara o objecto pelo JPA (é
//			// monitorizado). Desta forma, não há necessidade de ir
//			// buscar o objecto à base de dados.
//
//			persistData(entity, obj);
//
//			repository.save(entity);
//
//			return entity;
//		} catch (NoSuchElementException e) {
//			throw new ResourceNotFoundException(id);
//		}
//	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	private void persistData(StockMovement entity, StockMovementInsertDTO obj) {
		entity.setQuantity(obj.getQuantity());
		entity.setMovement(obj.getMovement());
	}

}
