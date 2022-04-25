package com.internship.retail_management.dto;

import com.internship.retail_management.entities.Iva;
import com.internship.retail_management.entities.enums.IvaValues;
import com.internship.retail_management.services.Tax;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IvaInsertDTO implements Tax {

	private IvaValues value;

	public IvaInsertDTO(Iva entity) {
		this.value = entity.getValue();
	}

	@Override
	public Double getTax() {
		return Double.valueOf(getValue().getCode()) / 100;
	}

}
