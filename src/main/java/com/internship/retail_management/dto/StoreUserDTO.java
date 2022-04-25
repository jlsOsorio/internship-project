package com.internship.retail_management.dto;

import com.internship.retail_management.entities.Store;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoreUserDTO {

	private Long id;
	private String address;
	private String council;
	private String zipCode;
	private String contact;
	
	public StoreUserDTO(Store entity) {
		this.id = entity.getId();
		this.address = entity.getAddress();
		this.council = entity.getCouncil();
		this.zipCode = entity.getZipCode();
		this.contact = entity.getContact();
	}
}
