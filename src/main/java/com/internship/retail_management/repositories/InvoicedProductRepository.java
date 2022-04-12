package com.internship.retail_management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.internship.retail_management.entities.InvoicedProduct;

//Aqui não é necessário registar @Repository porque já está a herdar de uma componente do Spring
public interface InvoicedProductRepository extends JpaRepository<InvoicedProduct, Long>{

}
