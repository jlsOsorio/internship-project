package com.internship.retail_management.dto;

import java.util.HashMap;
import java.util.Map;

import com.internship.retail_management.entities.Invoice;
import com.internship.retail_management.entities.enums.TransactionType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InvoiceInsertDTO {

	private TransactionType transaction;
	private Long userId;
	private Long cashRegisterId;
	
	@Setter(AccessLevel.NONE)
	private Map<String, Integer> invoicedProducts = new HashMap<>();
	
	public InvoiceInsertDTO(TransactionType transaction, Long userId, Long cashRegisterId) {
		this.transaction = transaction;
		this.userId = userId;
		this.cashRegisterId = cashRegisterId;
	}
	
	public InvoiceInsertDTO(Invoice entity) {
		this.transaction = entity.getTransaction();
		this.userId = entity.getUser().getId();
		this.cashRegisterId = entity.getCashRegister().getId();
	}
}
