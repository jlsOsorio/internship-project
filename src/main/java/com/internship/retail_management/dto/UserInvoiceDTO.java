package com.internship.retail_management.dto;

import com.internship.retail_management.entities.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInvoiceDTO {

	private Long id;
	private String name;
	private Long nif;
	
	public UserInvoiceDTO(User entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.nif = entity.getNif();
	}
}
