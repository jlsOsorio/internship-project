package com.internship.retail_management.dto;

import java.time.Instant;

import com.internship.retail_management.entities.User;
import com.internship.retail_management.entities.enums.Category;
import com.internship.retail_management.entities.enums.Status;

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
 * @param name      user's name
 * @param email     user's email
 * @param phone     user's phone number
 * @param birthDate user's birth date
 * @param nif       user's identification number
 * @param category  user's category
 * @param status    user's status
 * @param address   user's address
 * @param council   user's council
 * @param zipCode   user's zip code
 * @param storeUserDTO     user's store (with specific attributes)
 */
public class UserUpdateDTO {

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
	private Long storeId;
	
	/**
	 * Retrieves user.
	 * 
	 * @param entity
	 */
	public UserUpdateDTO(User entity) {
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
		this.storeId = entity.getStore().getId();
	}
}
