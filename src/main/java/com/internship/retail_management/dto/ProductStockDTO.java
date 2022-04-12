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
public class ProductStockDTO {

	private Integer stock;
	
	public ProductStockDTO(Product entity) {
		super();
		this.stock = entity.getStock();
	}
}
