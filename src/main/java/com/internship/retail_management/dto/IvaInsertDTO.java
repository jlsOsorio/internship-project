package com.internship.retail_management.dto;

import com.internship.retail_management.entities.Iva;
import com.internship.retail_management.entities.enums.IvaValues;
import com.internship.retail_management.services.Tax;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class IvaInsertDTO implements Tax {

	private Integer value;

	public IvaInsertDTO(Iva entity) {
		setValue(entity.getValue());
	}
	
	/**
	 * Gets IVA value.
	 * 
	 * @return value of IVA
	 */
	public IvaValues getValue() {
		return IvaValues.valueOf(value);
	}

	/**
	 * Sets IVA value.
	 * 
	 * @param value
	 */
	public void setValue(IvaValues value) {
		if (value != null) {
			this.value = value.getCode();
		}
	}

	@Override
	public Double getTax() {
		return Double.valueOf(value) / 100;
	}

}
