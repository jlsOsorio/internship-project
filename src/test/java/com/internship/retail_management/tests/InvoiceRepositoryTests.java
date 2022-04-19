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
import com.internship.retail_management.entities.enums.TransactionType;
import com.internship.retail_management.repositories.InvoiceRepository;

/**
 * This class tests if you can save and get a invoice.
 * 
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 */
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InvoiceRepositoryTests {
	
	@Autowired
	private InvoiceRepository invoiceRepository;
	
	/**
	 * Saves a invoice and tests if that invoice is saved by
	 * searching its id and checking if it's greater than 0.
	 * 
	 */
	@Test
	@Order(1)
	@Rollback(value = false)
	public void InvoiceSaveTest() {
		Invoice in1 = new Invoice(null, Instant.now(), TransactionType.DEBIT, null, null);
		Invoice in2 = new Invoice(null, Instant.now(), TransactionType.CREDIT, null, null);
		
		invoiceRepository.saveAll(Arrays.asList(in1, in2));
		
		Assertions.assertThat(in1.getInvoiceNumber()).isGreaterThan(0);
		Assertions.assertThat(in2.getInvoiceNumber()).isGreaterThan(0);
	}
	
	/**
	 * Gets a invoice with a specific id and confirms 
	 * that the id matches.
	 * 
	 */
	@Test
	@Order(2)
	public void getInvoiceTest() {	
		
		Invoice in = invoiceRepository.findById(1L).get();
		Assertions.assertThat(in.getInvoiceNumber()).isEqualTo(1L);

	}
	
	/**
	 * Gets all the invoices and confirms that the size
	 * of the invoice list is greater than 0.
	 * 
	 */	
	@Test
	@Order(3)
	public void getInvoicesTest() {
		
		List<Invoice> tb_invoice = invoiceRepository.findAll();
		Assertions.assertThat(tb_invoice.size()).isGreaterThan(0);
		
	}
}
