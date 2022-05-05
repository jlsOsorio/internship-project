package com.internship.retail_management.dto;

import com.internship.retail_management.entities.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDTO {

	private String password;
	
	public ChangePasswordDTO(User entity) {
		this.password = entity.getPassword();
	}
}
