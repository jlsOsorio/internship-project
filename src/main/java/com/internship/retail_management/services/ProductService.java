package com.internship.retail_management.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.internship.retail_management.entities.Product;
import com.internship.retail_management.repositories.ProductRepository;
import com.internship.retail_management.services.exceptions.StockException;

@Service //regista a classe como componente do Spring para ele conhecer e ser automaticamente injectada (autowired). Existem também o Component e o Repository, para o mesmo fim
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
//	@Autowired
//	private ProductRepository smRepository;
	
	public List<Product> findAll() {
		return repository.findAll();
	}
	
	public Product findById(Long id) {
		Optional<Product> obj = repository.findById(id);
		return obj.get();
	}
	
	public Product insert(Product obj) {
		if (obj.getStock() < 0) {
			throw new StockException(obj.getStock());
		}
		return repository.save(obj);
	}
	
//	public ProductStockDTO addStock(Long id, Integer qty) {
//		try
//		{
//			Product entity = repository.getOne(id); //o getOne prepara o objecto pelo JPA (é monitorizado). Desta forma, não há necessidade de ir buscar o objecto à base de dados.
//			entity.addStock(qty);
//			repository.save(entity);
//			ProductStockDTO updatedObj = new ProductStockDTO(entity);
//			return updatedObj;
//		}
//		catch (EntityNotFoundException e)
//		{
//			throw new ResourceNotFoundException(id);
//		}
//	}
	
}
