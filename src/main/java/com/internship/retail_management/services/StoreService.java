package com.internship.retail_management.services;

import java.util.IllegalFormatException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.internship.retail_management.dto.StoreInsertDTO;
import com.internship.retail_management.entities.CashRegister;
import com.internship.retail_management.entities.Store;
import com.internship.retail_management.repositories.StoreRepository;
import com.internship.retail_management.services.exceptions.DatabaseException;
import com.internship.retail_management.services.exceptions.ResourceNotFoundException;
import com.internship.retail_management.services.exceptions.ServiceException;

@Service
public class StoreService {

	@Autowired
	private StoreRepository repository;

	@Autowired
	private CashRegisterService crService;

	public List<Store> findAll() {
		return repository.findAll();
	}

	public Store findById(Long id) {
		try {
			Optional<Store> obj = repository.findById(id);
			return obj.get();
		} catch (NoSuchElementException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	@Transactional
	public Store insert(StoreInsertDTO store) {
		try {
			if (store.getNumberCashRegisters() <= 0) {
				throw new ServiceException("The store must have, at least, one cash register.");
			}

			Store obj = new Store();
			persistData(obj, store);

			obj = repository.save(obj);

			for (int i = 0; i < store.getNumberCashRegisters(); i++) {
				crService.insert(new CashRegister(null, obj));
			}

			obj.getCashRegisters().addAll(crService.findByStore(obj));

			return obj;
		} catch (IllegalFormatException e) {
			throw new ServiceException("Something went wrong!");
		}
	}

	@Transactional
	public Store update(Long id, StoreInsertDTO obj) {
		try {
			Store entity = repository.findById(id).get();

			persistData(entity, obj);
			int diffNumberCR = Math.abs(obj.getNumberCashRegisters() - entity.getCashRegisters().size());
			if (entity.getCashRegisters().size() < obj.getNumberCashRegisters()) {
				for (int i = 0; i < diffNumberCR; i++) {
					CashRegister newCR = crService.insert(new CashRegister(null, entity));
					entity.getCashRegisters().add(newCR);
				}
			} else if (entity.getCashRegisters().size() > obj.getNumberCashRegisters()) {
				for (int i = 0; i < diffNumberCR; i++) {
					Long crID = entity.getCashRegisters().get(entity.getCashRegisters().size() - 1).getId();
					entity.getCashRegisters().remove(entity.getCashRegisters().size() - 1);
					crService.delete(crID);
				}
			}

			return repository.save(entity);

		} catch (NoSuchElementException e) {
			throw new ResourceNotFoundException(id);
		} catch (IllegalFormatException e) {
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

	private void persistData(Store entity, StoreInsertDTO obj) {
		entity.setAddress(obj.getAddress());
		entity.setCouncil(obj.getCouncil());
		entity.setZipCode(obj.getZipCode());
		entity.setContact(obj.getContact());
		entity.setStatus(obj.getStatus());
	}
}
