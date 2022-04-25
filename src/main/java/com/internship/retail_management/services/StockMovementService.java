package com.internship.retail_management.services;

import java.util.IllegalFormatException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.internship.retail_management.dto.ProductDTO;
import com.internship.retail_management.dto.StockMovementInsertDTO;
import com.internship.retail_management.entities.InvoicedProduct;
import com.internship.retail_management.entities.Product;
import com.internship.retail_management.entities.StockMovement;
import com.internship.retail_management.entities.enums.Movement;
import com.internship.retail_management.repositories.StockMovementRepository;
import com.internship.retail_management.services.exceptions.DatabaseException;
import com.internship.retail_management.services.exceptions.ResourceNotFoundException;
import com.internship.retail_management.services.exceptions.ServiceException;
import com.internship.retail_management.services.exceptions.StockException;

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

	@Transactional
	public StockMovement insertStock(Long productId, StockMovementInsertDTO dto) {
		try {

			StockMovement obj = new StockMovement();
			persistData(obj, dto);

			ProductDTO stockProdDTO = productService.findById(productId);

			Integer newStock = stockProdDTO.getStock();

			if (obj.getQuantity() <= 0) {
				throw new StockException("Invalid quantity: " + obj.getQuantity());
			}

			if (obj.getMovement() == Movement.IN) {
				newStock += obj.getQuantity();
			}

			if (obj.getMovement() == Movement.OUT) {
				if (newStock - obj.getQuantity() < 0) {
					throw new StockException("There is not enough quantity: Product stock: " + newStock
							+ "; quantity required to sell: " + obj.getQuantity());
				}
				newStock -= obj.getQuantity();
			}

			stockProdDTO.getStockMovements().add(obj);
			StockMovement stockMovement = repository.save(obj);

			productService.updateStock(productId, newStock);

			return stockMovement;
		} catch (IllegalFormatException e) {
			throw new ServiceException("Something went wrong!");
		}

	}

	@Transactional
	public void insertInvoicedProduct(InvoicedProduct obj) {
		try {

			if (obj.getProduct().getStock() - obj.getQuantity() < 0) {
				throw new StockException("There is not enough quantity: Product stock: " + obj.getProduct().getStock()
						+ "; quantity required to sell: " + obj.getQuantity());
			}

			obj.getProduct().updateInvoicedStock();

			StockMovement stockMovement = obj.getProduct().getStockMovements()
					.get(obj.getProduct().getStockMovements().size() - 1);

			repository.save(stockMovement);

			productService.updateStock(obj.getProduct().getId(), obj.getProduct().getStock());

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

	private void persistData(StockMovement entity, StockMovementInsertDTO obj) {
		entity.setQuantity(obj.getQuantity());
		entity.setMovement(obj.getMovement());
	}

}
