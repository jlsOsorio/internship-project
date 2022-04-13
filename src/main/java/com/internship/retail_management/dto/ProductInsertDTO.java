package com.internship.retail_management.dto;

import java.io.Serializable;

import com.internship.retail_management.entities.Product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductInsertDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String name;
	private Integer stock;
	private Integer ivaValue;
	private Double grossPrice;
	
	public ProductInsertDTO(String name, Integer stock, Integer ivaValue, Double grossPrice) {
		super();
		this.name = name;
		this.stock = stock;
		this.ivaValue = ivaValue;
		this.grossPrice = grossPrice;
	}
	
	public ProductInsertDTO(Product entity) {
		super();
		this.name = entity.getName();
		this.stock = entity.getStock();
		this.ivaValue = entity.getIvaValue().getValue().getCode();
		this.grossPrice = entity.getGrossPrice();
	}
	
//	public void setIvaValue(Integer ivaValue) {
//		this.ivaValue.setValue(IvaValues.valueOf(ivaValue));
//	}

}
