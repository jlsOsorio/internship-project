package com.internship.retail_management.tests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
import com.internship.retail_management.entities.enums.IvaValues;
import com.internship.retail_management.repositories.IvaRepository;
import com.internship.retail_management.repositories.ProductRepository;

/**
 * This class tests if you can save a product,
 * get a product, get multiple products,
 * update a product and delete a product.
 * 
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 */
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductRepositoryTests {
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private IvaRepository ivaRepository;
	
	/**
	 * Saves a product and tests if that product is saved by
	 * searching its id and checking if it's greater than 0.
	 * 
	 */
	@Test
	@Order(1)
	@Rollback(value = false)
	public void saveProductTest() {
		
		Iva i1 = new Iva(null, IvaValues.NULL);
		Iva i2 = new Iva(null, IvaValues.LOW);
		Iva i3 = new Iva(null, IvaValues.INTERMEDIATE);
		Iva i4 = new Iva(null, IvaValues.NORMAL);
		
		ivaRepository.saveAll(Arrays.asList(i1, i2, i3, i4));

		Product product1 = new Product(null, "pao", 10, 0.50, i4);
		Product product2 = new Product(null, "agua", 50, 0.70, i3);
		productRepository.save(product1);
		productRepository.save(product2);
		Assertions.assertThat(product1.getId()).isGreaterThan(0);
		Assertions.assertThat(product2.getId()).isGreaterThan(0);
	}

	/**
	 * Gets a product with a specific id and confirms 
	 * that the id matches.
	 * 
	 */
	@Test
	@Order(2)
	public void getProductTest() {

		Product productTest = productRepository.findById(1L).get();
		Assertions.assertThat(productTest.getId()).isEqualTo(1L);

	}

	/**
	 * Gets all the products and confirms that the size
	 * of the product list is greater than 0.
	 * 
	 */
	@Test
	@Order(3)
	public void getProductsTest() {

		List<Product> products = productRepository.findAll();
		Assertions.assertThat(products.size()).isGreaterThan(0);

	}

//	/**
//	 * Finds a product by id and updates its stock.
//	 * Then it checks if the current stock value matches
//	 * our requested value.
//	 * 
//	 */
//	@Test
//	@Order(4)
//	public void updateProductTest() {
//		Product productTest = productRepository.findById(1L).get();
//		productTest.setStock(15);
//		Product productUpdated = productRepository.save(productTest);
//		Assertions.assertThat(productUpdated.getStock()).isEqualTo(15);
//	}
//
//	/**
//	 * Finds a product by id and deletes it. Then
//	 * checks for the product by name and sees if it finds
//	 * the deleted product, by returning null confirms
//	 * that the product was successfully deleted.
//	 */
//	@Test
//	@Order(5)
//	public void deleteProductTest() {
//		Product productTest = productRepository.findById(1L).get();
//		productRepository.delete(productTest);
//		Product productNull = null;
//		Product productT = productRepository.findByName("pao");
//		Optional<Product> optionalProduct = Optional.ofNullable(productT);
//		if (optionalProduct.isPresent()) {
//			productNull = optionalProduct.get();
//		}
//		Assertions.assertThat(productNull).isNull();
//	}
//
//	/**
//	 * Deletes a product by id. Then checks for the
//	 * product by name and sees if it finds
//	 * the deleted product, by returning null confirms
//	 * that the product was successfully deleted.
//	 */
//	@Test
//	@Order(6)
//	public void deleteProductIdTest() {
//		productRepository.deleteById(2L);
//		Product productNull = null;
//		Product productT = productRepository.findByName("agua");
//		Optional<Product> optionalProduct = Optional.ofNullable(productT);
//		if (optionalProduct.isPresent()) {
//			productNull = optionalProduct.get();
//		}
//		Assertions.assertThat(productNull).isNull();
//	}

}
