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
import com.internship.retail_management.entities.enums.TransactionType;
import com.internship.retail_management.repositories.StockMovementRepository;
import com.internship.retail_management.services.exceptions.DatabaseException;
import com.internship.retail_management.services.exceptions.ResourceNotFoundException;
import com.internship.retail_management.services.exceptions.ServiceException;
import com.internship.retail_management.services.exceptions.StockException;

@Service
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
			
			if (obj.getQuantity() <= 0) {
				throw new StockException("Invalid quantity: " + obj.getQuantity());
			}
			
			ProductDTO stockProdDTO = productService.findById(productId);
			Product stockProd = productService.productFromProductDTO(stockProdDTO);

			if (stockProd.getStock() - obj.getQuantity() < 0 && obj.getMovement() == Movement.OUT) {
				throw new StockException("There is not enough quantity: Product stock: " + stockProd.getStock()
						+ "; quantity required to sell: " + obj.getQuantity());
			}

			stockProd.updateStock(dto.getQuantity(), dto.getMovement());
			
			StockMovement stockMovement = repository.save(obj);

			productService.updateStock(productId, stockProd.getStock());

			return stockMovement;
		} catch (IllegalFormatException e) {
			throw new ServiceException("Something went wrong!");
		}

	}

	@Transactional
	public void insertInvoicedProduct(InvoicedProduct obj) {
		try {

			if (obj.getProduct().getStock() - obj.getQuantity() < 0 && obj.getProduct().getInvoicedProducts().get((obj.getProduct().getInvoicedProducts().size() - 1)).getInvoice().getTransaction() == TransactionType.DEBIT) {
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

	//auxiliary functions
	private void persistData(StockMovement entity, StockMovementInsertDTO obj) {
		entity.setQuantity(obj.getQuantity());
		entity.setMovement(obj.getMovement());
	}

}
