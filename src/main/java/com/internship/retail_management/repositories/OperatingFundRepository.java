package com.internship.retail_management.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.internship.retail_management.entities.OperatingFund;
import com.internship.retail_management.entities.User;

/**
 * Operating fund interface.
 * 
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 */
//Aqui não é necessário registar @Repository porque já está a herdar de uma componente do Spring
public interface OperatingFundRepository extends JpaRepository<OperatingFund, Long> {
	List<OperatingFund> findByUser(User user);
}
