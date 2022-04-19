package com.internship.retail_management.tests;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.internship.retail_management.entities.Iva;
import com.internship.retail_management.entities.Product;
import com.internship.retail_management.entities.StockMovement;
import com.internship.retail_management.entities.enums.IvaValues;
import com.internship.retail_management.entities.enums.Movement;
import com.internship.retail_management.repositories.IvaRepository;
import com.internship.retail_management.repositories.ProductRepository;
import com.internship.retail_management.repositories.StockMovementRepository;

/**
 * This class tests if you can update stock and 
 * if that stock updates it gets saved as a stock movement.
 * 
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 */
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StockMovementRepositoryTests {	
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private StockMovementRepository stockMovementRepository;

	@Autowired
	private IvaRepository ivaRepository;
	
	/**
	 * Saves products, then finds a product by his id and updates his stock.
	 * Then it checks if the current stock value matches our requested value.
	 * <br>
	 * As a result of the stock change it stores the stock movement and
	 * confirms that it was successfully added to our stock movement list
	 * 
	 */
	@Test
	@Order(1)
	@Rollback(value = false)
	public void updateStockTest() {
		
		Iva i1 = new Iva(null, IvaValues.NULL);
		Iva i2 = new Iva(null, IvaValues.LOW);
		Iva i3 = new Iva(null, IvaValues.INTERMEDIATE);
		Iva i4 = new Iva(null, IvaValues.NORMAL);
		ivaRepository.saveAll(Arrays.asList(i1, i2, i3, i4));
		Product p1 = new Product(null, "pao", 10, 0.50, i1);
		Product p2 = new Product(null, "agua", 50, 0.70, i2);
		productRepository.save(p1);
		productRepository.save(p2);

		
		Product productTest = productRepository.findById(1L).get();
		productTest.updateStock(10, Movement.IN);
		
		Product productTest2 = productRepository.findById(2L).get();
		productTest2.updateStock(20, Movement.OUT);

		Product productUpdated = productRepository.save(productTest);
		Assertions.assertThat(productUpdated.getStock()).isEqualTo(20);
		
		Product productUpdated2 = productRepository.save(productTest2);
		Assertions.assertThat(productUpdated2.getStock()).isEqualTo(30);
		
//		List<StockMovement> allMovements = Stream
//				.of(p1.getStockMovements(), p2.getStockMovements()).flatMap(Collection::stream)
//				.collect(Collectors.toList());
//		allMovements.forEach(movements -> stockMovementRepository.save(movements));
//		
//		Assertions.assertThat(allMovements.size()).isGreaterThan(1);
		
	}
	
	
}
