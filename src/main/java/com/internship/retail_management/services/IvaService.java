package com.internship.retail_management.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.internship.retail_management.dto.IvaInsertDTO;
import com.internship.retail_management.entities.Iva;
import com.internship.retail_management.repositories.IvaRepository;
import com.internship.retail_management.services.exceptions.DatabaseException;
import com.internship.retail_management.services.exceptions.ResourceNotFoundException;
import com.internship.retail_management.services.exceptions.ServiceException;

@Service
public class IvaService {

	@Autowired
	private IvaRepository repository;

	public List<Iva> findAll() {
		return repository.findAll();
	}

	public Iva findById(Long id) {
		try {
			Optional<Iva> obj = repository.findById(id);
			return obj.get();
		} catch (NoSuchElementException e) {
			throw new ResourceNotFoundException(id);
		}

	}

	public Iva insert(IvaInsertDTO obj) {
		try {
			Iva iva = new Iva();
			persistData(iva, obj);

			return repository.save(iva);
		} catch (IllegalArgumentException e) {
			throw new ServiceException("Something went wrong!");
		}
	}
	
	public Iva update(Long id, IvaInsertDTO obj) {
		try {
			Iva iva = repository.findById(id).get();
			
			persistData(iva, obj);

			return repository.save(iva);
		} catch (NoSuchElementException e) {
			throw new ServiceException("Something went wrong!");
		}
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	//auxiliary functions
	private void persistData(Iva obj, IvaInsertDTO dto) {
		obj.setValue(dto.getValue());
	}

}
