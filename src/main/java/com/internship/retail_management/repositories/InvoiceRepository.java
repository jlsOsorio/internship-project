package com.internship.retail_management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.internship.retail_management.entities.Invoice;

/**
 * Invoice interface.
 * 
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 */
//Aqui não é necessário registar @Repository porque já está a herdar de uma componente do Spring
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

}
