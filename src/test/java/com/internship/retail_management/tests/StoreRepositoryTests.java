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

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StoreRepositoryTests {

	@Autowired
	private StoreRepository storeRepository;

	// Save Store
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

	// Get store with specific ID
	@Test
	@Order(2)
	public void getStoreTest() {

		Store storeTest = storeRepository.findById(1L).get();
		Assertions.assertThat(storeTest.getId()).isEqualTo(1L);

	}

	// Get all stores
	@Test
	@Order(3)
	public void getStoresTest() {

		List<Store> stores = storeRepository.findAll();
		Assertions.assertThat(stores.size()).isGreaterThan(0);

	}

	// Update store (status)
	@Test
	@Order(4)
	public void updateStoreTest() {
		Store storeTest = storeRepository.findById(1L).get();
		storeTest.setStatus(Status.INACTIVE);
		Store storeUpdated = storeRepository.save(storeTest);
		Assertions.assertThat(storeUpdated.getStatus()).isEqualTo(Status.INACTIVE);
	}

	// Delete store
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

	// Delete store by id
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
