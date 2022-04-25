package com.internship.retail_management.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.internship.retail_management.entities.CashRegister;
import com.internship.retail_management.entities.Invoice;
import com.internship.retail_management.entities.enums.TransactionType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InvoiceDTO {

	private Long invoiceNumber;
	private TransactionType transaction;
	private UserInvoiceDTO user;
	private CashRegister cashRegister;

	@Setter(AccessLevel.NONE)
	List<InvoicedProductDTO> invoicedProducts = new ArrayList<>();
	private Double totalNoIva;
	private Double totalIva;

	public InvoiceDTO(Long invoiceNumber, TransactionType transaction, UserInvoiceDTO userInvoiceDTO,
			CashRegister cashRegister) {
		this.invoiceNumber = invoiceNumber;
		this.transaction = transaction;
		this.user = userInvoiceDTO;
		this.cashRegister = cashRegister;
	}

	public InvoiceDTO(Invoice entity) {
		UserInvoiceDTO userInvoiceDTO = new UserInvoiceDTO(entity.getUser());
		this.invoiceNumber = entity.getInvoiceNumber();
		this.transaction = entity.getTransaction();
		this.user = userInvoiceDTO;
		this.cashRegister = entity.getCashRegister();
		this.totalNoIva = entity.getTotalNoIva();
		this.totalIva = entity.getTotalIva();
		this.invoicedProducts.addAll(entity.getInvoicedProducts().stream()
				.map(invoicedProduct -> new InvoicedProductDTO(invoicedProduct)).collect(Collectors.toList()));
	}
}
