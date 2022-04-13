package com.internship.retail_management.tests;

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

import com.internship.retail_management.entities.Store;
import com.internship.retail_management.entities.enums.Status;
import com.internship.retail_management.repositories.StoreRepository;

/**
 * This class tests if you can save a store,
 * get a store, get multiple stores,
 * update a store and delete a store.
 * 
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 */
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StoreRepositoryTests {

	@Autowired
	private StoreRepository storeRepository;

	/**
	 * Saves a store and tests if that store is saved by
	 * searching its id and checking if it's greater than 0.
	 * 
	 */
	@Test
	@Order(1)
	@Rollback(value = false)
	public void saveStoreTest() {

		Store store1 = new Store(null, "Rua de cima", "Porto", "1234-123", "221231212", Status.ACTIVE);
		Store store2 = new Store(null, "Rua de baixo", "Gaia", "4321-321", "223212121", Status.INACTIVE);
		storeRepository.save(store1);
		storeRepository.save(store2);
		Assertions.assertThat(store1.getId()).isGreaterThan(0);
		Assertions.assertThat(store2.getId()).isGreaterThan(0);
	}

	/**
	 * Gets a store with a specific id and confirms 
	 * that the id matches.
	 * 
	 */
	@Test
	@Order(2)
	public void getStoreTest() {

		Store storeTest = storeRepository.findById(1L).get();
		Assertions.assertThat(storeTest.getId()).isEqualTo(1L);

	}

	/**
	 * Gets all the stores and confirms that the size
	 * of the store list is greater than 0.
	 * 
	 */
	@Test
	@Order(3)
	public void getStoresTest() {

		List<Store> stores = storeRepository.findAll();
		Assertions.assertThat(stores.size()).isGreaterThan(0);

	}

	/**
	 * Finds a store by id and updates its status.
	 * Then it checks if the current status value matches
	 * our requested value.
	 * 
	 */
	@Test
	@Order(4)
	public void updateStoreTest() {
		Store storeTest = storeRepository.findById(1L).get();
		storeTest.setStatus(Status.INACTIVE);
		Store storeUpdated = storeRepository.save(storeTest);
		Assertions.assertThat(storeUpdated.getStatus()).isEqualTo(Status.INACTIVE);
	}

	/**
	 * Finds a store by id and deletes it. Then
	 * checks for the store by zip code and sees if it finds
	 * the deleted store, by returning null confirms
	 * that the store was successfully deleted.
	 */
	@Test
	@Order(5)
	public void deleteStoreTest() {
		Store storeTest = storeRepository.findById(1L).get();
		storeRepository.delete(storeTest);
		Store storeNull = null;
		Store storeT = storeRepository.findByZipCode("1234-123");
		Optional<Store> optionalStore = Optional.ofNullable(storeT);
		if (optionalStore.isPresent()) {
			storeNull = optionalStore.get();
		}
		Assertions.assertThat(storeNull).isNull();
	}

	/**
	 * Deletes a store by id. Then checks for the
	 * store by zip code and sees if it finds
	 * the deleted store, by returning null confirms
	 * that the store was successfully deleted.
	 */
	@Test
	@Order(6)
	public void deleteStoreIdTest() {
		storeRepository.deleteById(2L);
		Store storeNull = null;
		Store storeT = storeRepository.findByZipCode("4321-321");
		Optional<Store> optionalStore = Optional.ofNullable(storeT);
		if (optionalStore.isPresent()) {
			storeNull = optionalStore.get();
		}
		Assertions.assertThat(storeNull).isNull();
	}

}
