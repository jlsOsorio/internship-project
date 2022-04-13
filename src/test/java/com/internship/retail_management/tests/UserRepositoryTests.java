package com.internship.retail_management.tests;

import java.time.Instant;
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

import com.internship.retail_management.entities.Store;
import com.internship.retail_management.entities.User;
import com.internship.retail_management.entities.enums.Category;
import com.internship.retail_management.entities.enums.Status;
import com.internship.retail_management.repositories.StoreRepository;
import com.internship.retail_management.repositories.UserRepository;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryTests {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private StoreRepository storeRepository;
	// Save User
	@Test
	@Order(1)
	@Rollback(value = false)
	public void saveUserTest() {
		
		Store s1 = new Store(null, "Rua de cima", "Porto", "1234-123", "221231212", Status.ACTIVE);
		Store s2 = new Store(null, "Rua de baixo", "Gaia", "4321-321", "223212121", Status.INACTIVE);
		
		storeRepository.saveAll(Arrays.asList(s1, s2));
		
		User userTest = new User(null, "Maria", "maria@gmail.com", "123456", "911232112", Instant.parse("1974-06-20T19:53:07Z"), 123456789L, Category.EMPLOYEE, Status.ACTIVE, "Rua de cima", "Maia", "1234-123", s1);
		User userTest2 = new User(null, "Quim", "quim@gmail.com", "123456", "911233112", Instant.parse("1980-06-20T19:53:07Z"), 123456784L, Category.EMPLOYEE, Status.ACTIVE, "Rua de baixo", "Gaia", "1235-123", s2);
		userRepository.save(userTest);
		userRepository.save(userTest2);
		Assertions.assertThat(userTest.getId()).isGreaterThan(0);
		Assertions.assertThat(userTest2.getId()).isGreaterThan(0);
	}
	
	
	// Get user with specific ID
	@Test
	@Order(2)
	public void getUserTest() {	
		
		User userTest = userRepository.findById(1L).get();		
		Assertions.assertThat(userTest.getId()).isEqualTo(1L);
		
	}
	
	// Get all users	
	@Test
	@Order(3)
	public void getUsersTest() {
		
		List<User> tb_user = userRepository.findAll();
		Assertions.assertThat(tb_user.size()).isGreaterThan(0);
		
	}
	
	// Update user (email)
	@Test
	@Order(4)
	public void updateUserTest() {
		User userTest = userRepository.findById(1L).get();
		userTest.setEmail("anamaria@gmail.com");
		User userUpdated = userRepository.save(userTest);
		Assertions.assertThat(userUpdated.getEmail()).isEqualTo("anamaria@gmail.com");
	}
	
	// Delete user
	@Test
	@Order(5)
	public void deleteUserTest() {
		User userTest = userRepository.findById(1L).get();
		userRepository.delete(userTest);
		User userNull = null;
		User userT = userRepository.findByEmail("anamaria@gmail.com");
		Optional<User> optionalUser = Optional.ofNullable(userT);
		
		if (optionalUser.isPresent()) {
			 userNull = optionalUser.get();
		}
		Assertions.assertThat(userNull).isNull();
	}
	
	// Delete user by id
	@Test
	@Order(6)
	public void deleteUserIdTest() {
		userRepository.deleteById(2L);
		User userNull = null;
		User userT = userRepository.findByEmail("quim@gmail.com");
		Optional<User> optionalUser = Optional.ofNullable(userT);
		if (optionalUser.isPresent()) {
			 userNull = optionalUser.get();
		}
		Assertions.assertThat(userNull).isNull();
	}
	
	
}
