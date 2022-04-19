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

import com.internship.retail_management.entities.CashRegister;
import com.internship.retail_management.entities.Store;
import com.internship.retail_management.entities.enums.Status;
import com.internship.retail_management.repositories.CashRegisterRepository;
import com.internship.retail_management.repositories.StoreRepository;

/**
 * This class tests if you can save and get a cash register.
 * 
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 */
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CashRegisterRepositoryTests {
	
	@Autowired
	private CashRegisterRepository cashRegisterRepository;
	
	@Autowired
	private StoreRepository storeRepository;
	
	/**
	 * Saves a cash register and tests if that cash register is saved by searching its id and
	 * checking if it's greater than 0.
	 * 
	 */
	@Test
	@Order(1)
	@Rollback(value = false)
	public void CashRegisterSaveTest() {
		
		Store s1 = new Store(null, "Rua de cima", "Porto", "1234-123", "221231212", Status.ACTIVE);
		Store s2 = new Store(null, "Rua de baixo", "Gaia", "4321-321", "223212121", Status.INACTIVE);

		storeRepository.saveAll(Arrays.asList(s1, s2));
		
		CashRegister c1 = new CashRegister(null, s1);
		CashRegister c2 = new CashRegister(null, s2);

		cashRegisterRepository.saveAll(Arrays.asList(c1, c2));
	}
	
	/**
	 * Gets a cash register with a specific id and confirms 
	 * that the id matches.
	 * 
	 */
	@Test
	@Order(2)
	public void getCashRegisterTest() {	
		
		CashRegister cr = cashRegisterRepository.findById(1L).get();
		Assertions.assertThat(cr.getId()).isEqualTo(1L);

	}
	
	/**
	 * Gets all the cash registers and confirms that the size
	 * of the cash register list is greater than 0.
	 * 
	 */	
	@Test
	@Order(3)
	public void getCashRegistersTest() {
		
		List<CashRegister> tb_cashRegister = cashRegisterRepository.findAll();
		Assertions.assertThat(tb_cashRegister.size()).isGreaterThan(0);
		
	}
	
}
