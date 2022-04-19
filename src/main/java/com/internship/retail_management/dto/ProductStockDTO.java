package com.internship.retail_management.dto;

import com.internship.retail_management.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data transfer object class for the product stock.
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductStockDTO {

	private Integer stock;
	
	/**
	 * Retrieves product stock.
	 * @param entity
	 */
	public ProductStockDTO(Product entity) {
		super();
		this.stock = entity.getStock();
	}
}
