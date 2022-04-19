package com.internship.retail_management.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.internship.retail_management.entities.CashRegister;
import com.internship.retail_management.entities.Store;

/**
 * Cash register interface.
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 */
//Aqui não é necessário registar @Repository porque já está a herdar de uma componente do Spring
public interface CashRegisterRepository extends JpaRepository<CashRegister, Long>{

	public List<CashRegister> findByStore(Store store);
}
