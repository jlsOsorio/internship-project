package com.internship.retail_management.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InvoicedProductDTO {

	private Long id;
	private Integer quantity;
	private Long userId;
	private Long cashRegisterId;
	
	@Setter(AccessLevel.NONE)
	private Map<String,Integer> invoicedProducts = new HashMap<>();
	
//	public InvoicedProductDTO(TransactionType transaction, Long userId, Long cashRegisterId) {
//		this.transaction = transaction;
//		this.userId = userId;
//		this.cashRegisterId = cashRegisterId;
//	}
	
}
