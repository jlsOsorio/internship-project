package com.internship.retail_management.dto;

import java.util.ArrayList;
import java.util.List;

import com.internship.retail_management.entities.Product;
import com.internship.retail_management.entities.StockMovement;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {

	private Long id;
	private String name;
	private Integer stock;
	private Integer ivaValue;
	private Double grossPrice;
	private Double taxedPrice;

	@Setter(AccessLevel.NONE)
	List<StockMovement> stockMovements = new ArrayList<>();

	public ProductDTO(Long id, String name, Integer stock, Integer ivaValue, Double grossPrice) {
		this.id = id;
		this.name = name;
		this.stock = stock;
		this.ivaValue = ivaValue;
		this.grossPrice = grossPrice;
		this.taxedPrice = grossPrice * this.ivaValue;
	}

	public ProductDTO(Product entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.stock = entity.getStock();
		this.ivaValue = entity.getIvaValue().getValue().getCode();
		this.grossPrice = entity.getGrossPrice();
		this.taxedPrice = entity.getTaxedPrice();
		this.stockMovements.addAll(entity.getStockMovements());
	}
	
}
