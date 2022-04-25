package com.internship.retail_management.services;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.internship.retail_management.dto.InvoiceDTO;
import com.internship.retail_management.dto.InvoiceInsertDTO;
import com.internship.retail_management.dto.InvoiceUpdateDTO;
import com.internship.retail_management.dto.ProductDTO;
import com.internship.retail_management.entities.Invoice;
import com.internship.retail_management.entities.InvoicedProduct;
import com.internship.retail_management.entities.Product;
import com.internship.retail_management.entities.User;
import com.internship.retail_management.repositories.InvoiceRepository;
import com.internship.retail_management.services.exceptions.DatabaseException;
import com.internship.retail_management.services.exceptions.ResourceNotFoundException;
import com.internship.retail_management.services.exceptions.ServiceException;

@Service
public class InvoiceService {

	@Autowired
	private InvoiceRepository repository;

	@Autowired
	private CashRegisterService cashRegisterService;

	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;

	@Autowired
	private InvoicedProductService invoicedProductService;

	public List<InvoiceDTO> findAll() {
		List<Invoice> list = repository.findAll();
		return list.stream().map(invoice -> new InvoiceDTO(invoice)).collect(Collectors.toList());
	}

	public InvoiceDTO findById(Long id) {
		try {
			Optional<Invoice> obj = repository.findById(id);
			return new InvoiceDTO(obj.get());
		} catch (NoSuchElementException e) {
			throw new ResourceNotFoundException(id);
		}

	}

	@Transactional // rollback total se acontecer algum erro em qualquer uma das comunicações com a
					// base de dados
	public InvoiceDTO insert(InvoiceInsertDTO dto) {
		try {
			Invoice obj = new Invoice();
			persistData(obj, dto);

			obj = repository.save(obj);

			for (String name : dto.getInvoicedProducts().keySet()) {
				ProductDTO productDTO = productService.findByName(name);
				Product product = productService.productFromProductDTO(productDTO);

				InvoicedProduct invoicedProduct = new InvoicedProduct();
				invoicedProduct.setProduct(product);
				invoicedProduct.setQuantity(dto.getInvoicedProducts().get(name));
				invoicedProduct.setInvoice(obj);
				invoicedProduct.setSubTotalNoIva();
				invoicedProduct.setSubTotalIva();
				obj.getInvoicedProducts().add(invoicedProduct);

				invoicedProductService.insert(invoicedProduct);

			}

			obj.setTotalNoIva();
			obj.setTotalIva();
			repository.save(obj);

			InvoiceDTO invoiceDTO = new InvoiceDTO(obj);

			return invoiceDTO;
		} catch (IllegalArgumentException e) {
			throw new ServiceException("Something went wrong!");
		}

	}

	public InvoiceDTO update(Long invoiceNumber, InvoiceUpdateDTO obj) {
		try {
			Invoice entity = repository.findById(invoiceNumber).get();

			updateData(entity, obj);

			repository.save(entity);

			return new InvoiceDTO(entity);
		} catch (NoSuchElementException e) {
			throw new ResourceNotFoundException(invoiceNumber);
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

	// auxiliary functions
	private void persistData(Invoice obj, InvoiceInsertDTO dto) {

		User user = userService.userFromUserDTO(userService.findById(dto.getUserId()));

		/////////////////// VERIFICAR SE CAIXA PERTENCE À LOJA ONDE ESTÁ O UTILIZADOR
		/////////////////// ///////////////////
		Boolean flag = false;

		Integer cashRegistersNumber = user.getStore().getCashRegisters().size();

		for (int i = 0; i < cashRegistersNumber; i++) {
			if (dto.getCashRegisterId() == user.getStore().getCashRegisters().get(i).getId()) {
				flag = true;
			}
		}

		if (flag == false) {
			throw new ServiceException("The cash register doesn't belong to the store where the employee belongs.");
		}
		///////////////////////////////////////////////////////////////////////////////////////////////

		obj.setDate(Instant.now());
		obj.setTransaction(dto.getTransaction());
		obj.setCashRegister(cashRegisterService.findById(dto.getCashRegisterId()));

		obj.setUser(user);

	}

	private void updateData(Invoice obj, InvoiceUpdateDTO dto) {

		obj.setDate(Instant.now());
		obj.setCashRegister(cashRegisterService.findById(dto.getCashRegisterId()));

		User user = userService.userFromUserDTO(userService.findById(dto.getUserId()));

		obj.setUser(user);

	}

}
