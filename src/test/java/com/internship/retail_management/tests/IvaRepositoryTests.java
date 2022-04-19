package com.internship.retail_management.tests;

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

import com.internship.retail_management.entities.Iva;
import com.internship.retail_management.entities.enums.IvaValues;
import com.internship.retail_management.repositories.IvaRepository;

/**
 * This class tests if you can save and get the
 * values of IVA.
 * 
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 */
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IvaRepositoryTests {

	@Autowired
	private IvaRepository ivaRepository;
	
	/**
	 * Saves a IVA and tests if that Iva is saved by
	 * searching its id and checking if it's greater than 0.
	 * 
	 */
	@Test
	@Order(1)
	@Rollback(value = false)
	public void IvaSaveTest() {
		Iva i1 = new Iva(null, IvaValues.NULL);
		Iva i2 = new Iva(null, IvaValues.LOW);
		Iva i3 = new Iva(null, IvaValues.INTERMEDIATE);
		Iva i4 = new Iva(null, IvaValues.NORMAL);

		ivaRepository.saveAll(Arrays.asList(i1, i2, i3, i4));
		
		Assertions.assertThat(i1.getId()).isGreaterThan(0);
		Assertions.assertThat(i2.getId()).isGreaterThan(0);
		Assertions.assertThat(i3.getId()).isGreaterThan(0);
		Assertions.assertThat(i4.getId()).isGreaterThan(0);
	}
	
	/**
	 * Gets a iva with a specific id and confirms 
	 * that the id matches.
	 * 
	 */
	@Test
	@Order(2)
	public void getIvaTest() {	
		
		Iva ivaTest = ivaRepository.findById(1L).get();		
		Assertions.assertThat(ivaTest.getId()).isEqualTo(1L);
		
	}
	
	/**
	 * Gets all the IVA's and confirms that the size
	 * of the IVA list is greater than 0.
	 * 
	 */	
	@Test
	@Order(3)
	public void getIvasTest() {
		
		List<Iva> tb_iva = ivaRepository.findAll();
		Assertions.assertThat(tb_iva.size()).isGreaterThan(0);
		
	}
}
