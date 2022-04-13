package com.internship.retail_management.dto;

import java.time.Instant;

import com.internship.retail_management.entities.Store;
import com.internship.retail_management.entities.User;
import com.internship.retail_management.entities.enums.Category;
import com.internship.retail_management.entities.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	private Long id;
	private String name;
	private String email;
	private String phone;
	private Instant birthDate;
	private Long nif;
	private Category category;
	private Status status;
	private String address;
	private String council;
	private String zipCode;
	private Store store;
	
	public UserDTO(User entity) {
		super();
		this.id = entity.getId();
		this.name = entity.getName();
		this.email = entity.getEmail();
		this.phone = entity.getPhone();
		this.birthDate = entity.getBirthDate();
		this.nif = entity.getNif();
		this.category = entity.getCategory();
		this.status = entity.getStatus();
		this.address = entity.getAddress();
		this.council = entity.getCouncil();
		this.zipCode = entity.getZipCode();
		this.store = entity.getStore();
	}
}
