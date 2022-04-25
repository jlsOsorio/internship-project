package com.internship.retail_management.dto;

import com.internship.retail_management.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductInsertDTO {

	private String name;
	private Integer stock;
	private Integer ivaValue;
	private Double grossPrice;

	public ProductInsertDTO(Product entity) {
		super();
		this.name = entity.getName();
		this.stock = entity.getStock();
		this.ivaValue = entity.getIvaValue().getValue().getCode();
		this.grossPrice = entity.getGrossPrice();
	}

}
