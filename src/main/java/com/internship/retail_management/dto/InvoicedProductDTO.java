package com.internship.retail_management.dto;

import com.internship.retail_management.entities.InvoicedProduct;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoicedProductDTO {

	private Long id;
	private Integer quantity;
	private Long productId;
	private String productName;
	private Integer ivaValue;
	private Double subTotalNoIva;
	private Double subTotalIva;
	
	public InvoicedProductDTO(InvoicedProduct entity) {
		this.id = entity.getId();
		this.quantity = entity.getQuantity();
		this.productId = entity.getProduct().getId();
		this.productName = entity.getProduct().getName();
		this.ivaValue = entity.getProduct().getIvaValue().getValue().getCode();
		this.subTotalNoIva = entity.getSubTotalNoIva();
		this.subTotalIva = entity.getSubTotalIva();
	}
}
