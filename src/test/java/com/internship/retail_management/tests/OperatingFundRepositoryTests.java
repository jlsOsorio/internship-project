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

import com.internship.retail_management.entities.OperatingFund;
import com.internship.retail_management.repositories.OperatingFundRepository;

/**
 * This class tests if you can save and get 
 * operating funds.
 * 
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 */
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OperatingFundRepositoryTests {

	@Autowired
	private OperatingFundRepository operatingFundRepository;
	
	/**
	 * Saves a operating Fund and tests if that operating fund is saved by
	 * searching its id and checking if it's greater than 0.
	 * 
	 */
	@Test
	@Order(1)
	@Rollback(value = false)
	public void OperatingFundSaveTest() {
		OperatingFund of1 = new OperatingFund(null, 500.00, 470.00, Instant.parse("2021-06-20T19:53:07Z"), null, null);
		OperatingFund of2 = new OperatingFund(null, 500.00, 530.00, Instant.parse("2021-06-20T19:53:07Z"), null, null);
		OperatingFund of3 = new OperatingFund(null, 470.00, 450.00, Instant.parse("2021-06-22T19:53:07Z"), null, null);
	
		operatingFundRepository.saveAll(Arrays.asList(of1, of2, of3));
		
		Assertions.assertThat(of1.getId()).isGreaterThan(0);
		Assertions.assertThat(of2.getId()).isGreaterThan(0);
		Assertions.assertThat(of3.getId()).isGreaterThan(0);
	}
	
	/**
	 * Gets a operating fund with a specific id and confirms 
	 * that the id matches.
	 * 
	 */
	@Test
	@Order(2)
	public void getOperatingFundTest() {	
		
		OperatingFund ofTest = operatingFundRepository.findById(1L).get();		
		Assertions.assertThat(ofTest.getId()).isEqualTo(1L);
		
	}
	
	/**
	 * Gets all the operating funds and confirms that the size
	 * of the operating fund list is greater than 0.
	 * 
	 */	
	@Test
	@Order(3)
	public void getOperatingFundsTest() {
		
		List<OperatingFund> tb_of = operatingFundRepository.findAll();
		Assertions.assertThat(tb_of.size()).isGreaterThan(0);
		
	}
}
