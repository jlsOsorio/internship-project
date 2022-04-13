package com.internship.retail_management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.internship.retail_management.entities.User;

//Aqui não é necessário registar @Repository porque já está a herdar de uma componente do Spring
public interface UserRepository extends JpaRepository<User, Long>{

	User findByEmail(String email);
	User findByNif(Long nif);
}
