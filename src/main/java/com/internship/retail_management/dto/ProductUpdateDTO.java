package com.internship.retail_management.dto;

import com.internship.retail_management.entities.Product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductUpdateDTO {
	
	private String name;
	private Integer ivaValue;
	private Double grossPrice;
	
	public ProductUpdateDTO(String name, Integer ivaValue, Double grossPrice) {
		super();
		this.name = name;
		this.ivaValue = ivaValue;
		this.grossPrice = grossPrice;
	}
	
	public ProductUpdateDTO(Product entity) {
		super();
		this.name = entity.getName();
		this.ivaValue = entity.getIvaValue().getValue().getCode();
		this.grossPrice = entity.getGrossPrice();
	}
	
//	public void setIvaValue(Integer ivaValue) {
//		this.ivaValue.setValue(IvaValues.valueOf(ivaValue));
//	}

}
