package com.internship.retail_management.dto;

import com.internship.retail_management.entities.Invoice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceUpdateDTO {

	private Long userId;
	private Long cashRegisterId;

	public InvoiceUpdateDTO(Invoice entity) {
		this.userId = entity.getUser().getId();
		this.cashRegisterId = entity.getCashRegister().getId();
	}
}
