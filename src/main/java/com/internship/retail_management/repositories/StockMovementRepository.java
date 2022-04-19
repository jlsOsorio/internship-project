package com.internship.retail_management.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.internship.retail_management.entities.Product;
import com.internship.retail_management.entities.StockMovement;

//Aqui não é necessário registar @Repository porque já está a herdar de uma componente do Spring
public interface StockMovementRepository extends JpaRepository<StockMovement, Long>{
	List<StockMovement> findByProduct(Product product);
}
