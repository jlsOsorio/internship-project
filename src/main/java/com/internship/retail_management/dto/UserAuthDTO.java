package com.internship.retail_management.dto;

import com.internship.retail_management.entities.User;
import com.internship.retail_management.entities.enums.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data transfer object class for the user.
 * 
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 *
 */
@Getter
@Setter
/**
 * Constructor with no arguments for the user DTO.
 */
@NoArgsConstructor
@AllArgsConstructor
/**
 * Constructor for the user DTO.
 * 
 * @param email     user's email
 * @param token     user's token
 */
public class UserAuthDTO {

	private Long id;
	private String email;
	private Category category;
	private String token;

	/**
	 * Retrieves user.
	 * 
	 * @param entity
	 */
	public UserAuthDTO(User entity) {
		this.id = entity.getId();
		this.email = entity.getEmail();
		this.category = entity.getCategory();
	}
}