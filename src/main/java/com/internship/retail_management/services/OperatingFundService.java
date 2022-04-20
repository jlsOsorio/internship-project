package com.internship.retail_management.services;

import java.time.Instant;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.internship.retail_management.dto.OperatingFundInsertDTO;
import com.internship.retail_management.dto.UserDTO;
import com.internship.retail_management.entities.OperatingFund;
import com.internship.retail_management.entities.User;
import com.internship.retail_management.repositories.OperatingFundRepository;
import com.internship.retail_management.services.exceptions.DatabaseException;
import com.internship.retail_management.services.exceptions.DateException;
import com.internship.retail_management.services.exceptions.ResourceNotFoundException;
import com.internship.retail_management.services.exceptions.ServiceException;

@Service // regista a classe como componente do Spring para ele conhecer e ser
			// automaticamente injectada (autowired). Existem também o Component e o
			// Repository, para o mesmo fim
public class OperatingFundService {

	@Autowired
	private OperatingFundRepository repository;

	@Autowired
	private UserService userService;
	
	@Autowired
	private CashRegisterService cashRegisterService;

	public List<OperatingFund> findAll() {
		return repository.findAll();
	}

	public OperatingFund findById(Long id) {
		Optional<OperatingFund> obj = repository.findById(id);
		return obj.get();
	}

	public List<OperatingFund> findByUser(Long userId) {
		try {
			UserDTO userDTO = userService.findById(userId);
			User user = userService.userFromUserDTO(userDTO);
			return repository.findByUser(user);
		} catch (NoSuchElementException e) {
			throw new ResourceNotFoundException(userId);
		}
	}

	public OperatingFund insert(Long userId, OperatingFundInsertDTO dto) {
		try {

			OperatingFund obj = new OperatingFund();
			persistData(obj, dto);

			if (obj.getEntryQty() <= 0) {
				throw new ServiceException("The entry quantity can't be negative.");
			}

			if (obj.getMoment().isAfter(Instant.now())) {
				throw new DateException("The inserted date must be before actual date.");
			}

			UserDTO userDTO = userService.findById(userId);
			User user = userService.userFromUserDTO(userDTO);

			obj.setUser(user);

			return repository.save(obj);
		} catch (IllegalFormatException e) {
			throw new ServiceException("Something went wrong!");
		} catch  (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}

	}

	public OperatingFund update(Long id, OperatingFundInsertDTO obj) {
		try {
			if (obj.getMoment().isAfter(Instant.now())) {
				throw new DateException("The inserted date must be before actual date.");
			}
			
			OperatingFund entity = repository.findById(id).get(); // o getOne (deprecated e, por isso, não usado) prepara o objecto pelo JPA (é
															// monitorizado). Desta forma, não há necessidade de ir
															// buscar o objecto à base de dados.


			persistData(entity, obj);
			
			

			repository.save(entity);

			return entity;
		} catch (NoSuchElementException e) {
			if (repository.findById(id).isEmpty())
			{
				throw new ResourceNotFoundException(id);
			}
			else
			{
				throw new ResourceNotFoundException(obj.getCashRegisterId());
			}
		} catch  (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
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
	
	private void persistData(OperatingFund obj, OperatingFundInsertDTO dto) {
		obj.setEntryQty(dto.getEntryQty());
		obj.setExitQty(dto.getExitQty());
		obj.setCashRegister(cashRegisterService.findById(dto.getCashRegisterId()));
		obj.setMoment(dto.getMoment());
	}

}
