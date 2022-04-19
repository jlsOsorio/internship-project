package com.internship.retail_management.dto;

import java.util.List;

import com.internship.retail_management.entities.CashRegister;
import com.internship.retail_management.entities.Store;
import com.internship.retail_management.entities.enums.Status;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoreInsertDTO {

	private String address;
	private String council;
	private String zipCode;
	private String contact;
	private Status status;
	private Integer numberCashRegisters;
	
	@Setter(AccessLevel.NONE)
	private List<CashRegister> cashRegisters;
	
	public StoreInsertDTO(Store entity) {
		this.address = entity.getAddress();
		this.council = entity.getCouncil();
		this.zipCode = entity.getZipCode();
		this.contact = entity.getContact();
		this.status = entity.getStatus();
	}
}
