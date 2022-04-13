package com.internship.retail_management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.internship.retail_management.entities.Iva;

//Aqui não é necessário registar @Repository porque já está a herdar de uma componente do Spring
public interface IvaRepository extends JpaRepository<Iva, Long> {

}
