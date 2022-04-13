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


@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductRepositoryTests {
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private IvaRepository ivaRepository;
	
	// Save Product
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

	// Get product with specific ID
	@Test
	@Order(2)
	public void getProductTest() {

		Product productTest = productRepository.findById(1L).get();
		Assertions.assertThat(productTest.getId()).isEqualTo(1L);

	}

	// Get all products
	@Test
	@Order(3)
	public void getProductsTest() {

		List<Product> products = productRepository.findAll();
		Assertions.assertThat(products.size()).isGreaterThan(0);

	}

	// Update product (stock)
	@Test
	@Order(4)
	public void updateProductTest() {
		Product productTest = productRepository.findById(1L).get();
		productTest.setStock(15);
		Product productUpdated = productRepository.save(productTest);
		Assertions.assertThat(productUpdated.getStock()).isEqualTo(15);
	}

	// Delete product
	@Test
	@Order(5)
	public void deleteProductTest() {
		Product productTest = productRepository.findById(1L).get();
		productRepository.delete(productTest);
		Product productNull = null;
		Product productT = productRepository.findByName("pao");
		Optional<Product> optionalProduct = Optional.ofNullable(productT);
		if (optionalProduct.isPresent()) {
			productNull = optionalProduct.get();
		}
		Assertions.assertThat(productNull).isNull();
	}

	// Delete product by id
	@Test
	@Order(6)
	public void deleteProductIdTest() {
		productRepository.deleteById(2L);
		Product productNull = null;
		Product productT = productRepository.findByName("agua");
		Optional<Product> optionalProduct = Optional.ofNullable(productT);
		if (optionalProduct.isPresent()) {
			productNull = optionalProduct.get();
		}
		Assertions.assertThat(productNull).isNull();
	}

}
