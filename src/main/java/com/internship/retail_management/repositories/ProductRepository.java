package com.internship.retail_management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.internship.retail_management.entities.Product;

/**
 * Product interface.
 * 
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 */
//Aqui não é necessário registar @Repository porque já está a herdar de uma componente do Spring
public interface ProductRepository extends JpaRepository<Product, Long> {

	Product findByName(String name);
}
