package com.internship.retail_management.tests;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.internship.retail_management.entities.Invoice;
import com.internship.retail_management.entities.InvoicedProduct;
import com.internship.retail_management.entities.Iva;
import com.internship.retail_management.entities.Product;
import com.internship.retail_management.entities.enums.IvaValues;
import com.internship.retail_management.entities.enums.TransactionType;
import com.internship.retail_management.repositories.InvoiceRepository;
import com.internship.retail_management.repositories.InvoicedProductRepository;
import com.internship.retail_management.repositories.IvaRepository;
import com.internship.retail_management.repositories.ProductRepository;

/**
 * This class tests if you can save and get a invoiced product.
 * 
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 */
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InvoicedProductRepositoryTests {

	@Autowired
	private InvoicedProductRepository invoicedProductRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private IvaRepository ivaRepository;
	/**
	 * Saves a invoiced product and tests if that invoiced product is saved by searching its id and
	 * checking if it's greater than 0.
	 * 
	 */
	@Test
	@Order(1)
	@Rollback(value = false)
	public void InvoicedProductSaveTest() {
		
		Invoice in1 = new Invoice(null, Instant.now(), TransactionType.DEBIT, null, null);
		Invoice in2 = new Invoice(null, Instant.now(), TransactionType.CREDIT, null, null);
		invoiceRepository.saveAll(Arrays.asList(in1, in2));
		
		Iva i1 = new Iva(null, IvaValues.NULL);
		Iva i2 = new Iva(null, IvaValues.LOW);
		Iva i3 = new Iva(null, IvaValues.INTERMEDIATE);
		Iva i4 = new Iva(null, IvaValues.NORMAL);
		ivaRepository.saveAll(Arrays.asList(i1, i2, i3, i4));
		
		Product p1 = new Product(null, "pao", 10, 0.50, i4);
		Product p2 = new Product(null, "agua", 50, 0.70, i3);
		Product p3 = new Product(null, "cereais", 20, 1.50, i2);
		productRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		InvoicedProduct ip1 = new InvoicedProduct(null, 5, p1, in1);
		InvoicedProduct ip2 = new InvoicedProduct(null, 10, p2, in1);
		InvoicedProduct ip3 = new InvoicedProduct(null, 10, p3, in2);
		invoicedProductRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
		
		Assertions.assertThat(ip1.getId()).isGreaterThan(0);
		Assertions.assertThat(ip2.getId()).isGreaterThan(0);
		Assertions.assertThat(ip3.getId()).isGreaterThan(0);
	}
	
	/**
	 * Gets a invoiced product with a specific id and confirms 
	 * that the id matches.
	 * 
	 */
	@Test
	@Order(2)
	public void getInvoicedProductTest() {	
		
		InvoicedProduct inP = invoicedProductRepository.findById(1L).get();
		Assertions.assertThat(inP.getId()).isEqualTo(1L);

	}
	
	/**
	 * Gets all the invoiced products and confirms that the size
	 * of the invoiced product list is greater than 0.
	 * 
	 */	
	@Test
	@Order(3)
	public void getInvoicedProductsTest() {
		
		List<InvoicedProduct> tb_invoicedProduct = invoicedProductRepository.findAll();
		Assertions.assertThat(tb_invoicedProduct.size()).isGreaterThan(0);
		
	}

}
