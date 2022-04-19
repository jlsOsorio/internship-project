package com.internship.retail_management.repositories;

<<<<<<< HEAD
import java.net.InterfaceAddress;
=======
import java.util.List;
>>>>>>> 9644dfcef947e975262f3a830a0736258c4bad5b

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
