package com.internship.retail_management.dto;

import java.time.Instant;

import com.internship.retail_management.entities.Store;
import com.internship.retail_management.entities.User;
import com.internship.retail_management.entities.enums.Category;
import com.internship.retail_management.entities.enums.Status;

/**
 * Data transfer object class for the user.
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 *
 */
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
	
	/**
	 * Constructor with no arguments for the user DTO.
	 */
	public UserDTO() {}

	/**
	 * Constructor for the user DTO.
	 * @param id user's id
	 * @param name user's name
	 * @param email user's email
	 * @param phone user's phone number
	 * @param birthDate user's birth date
	 * @param nif user's tax identification number
	 * @param category user's category
	 * @param status user's status
	 * @param address user's address
	 * @param council user's council
	 * @param zipCode user's zip code
	 * @param store user's store
	 */
	public UserDTO(Long id, String name, String email, String phone, Instant birthDate, Long nif, Category category,
			Status status, String address, String council, String zipCode, Store store) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.birthDate = birthDate;
		this.nif = nif;
		this.category = category;
		this.status = status;
		this.address = address;
		this.council = council;
		this.zipCode = zipCode;
		this.store = store;
	}
	
	/**
	 * Retrieves user.
	 * @param entity
	 */
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

	/**
	 * Getter for user's id.
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Setter for users's id.
	 * @param id user's id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter for user's name.
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for users's name.
	 * @param name user's name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for user's email.
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter for users's email.
	 * @param email user's email.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter for user's phone number.
	 * @return
	 */
	public String getPhone() {
		return phone;
	}
	
	/**
	 * Setter for users's phone number.
	 * @param phone user's phone number
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Getter for user's birth date.
	 * @return
	 */
	public Instant getBirthDate() {
		return birthDate;
	}

	/**
	 * Setter for users's birth date.
	 * @param birthDate user's birth date
	 */
	public void setBirthDate(Instant birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * Getter for user's tax identification number.
	 * @return
	 */
	public Long getNif() {
		return nif;
	}

	/**
	 * Setter for users's tax identification number.
	 * @param nif user's NIF
	 */
	public void setNif(Long nif) {
		this.nif = nif;
	}

	/**
	 * Getter for user's category.
	 * @return
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * Setter for users's category.
	 * @param category user's category
	 */
	public void setCategory(Category category) {
		this.category = category;
	}
	
	/**
	 * Getter for user's status
	 * @return
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Setter for users's status.
	 * @param status user's status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * Getter for user's address.
	 * @return
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Setter for users's address.
	 * @param address user's address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Getter for user's council.
	 * @return
	 */
	public String getCouncil() {
		return council;
	}

	/**
	 * Setter for users's council.
	 * @param council user's council
	 */
	public void setCouncil(String council) {
		this.council = council;
	}

	/**
	 * Getter for user's zip code.
	 * @return
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * Setter for users's zip code.
	 * @param zipCode user's zip code
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * Getter for user's store
	 * @return
	 */
	public Store getStore() {
		return store;
	}

	/**
	 * Setter for users's store
	 * @param store user's store
	 */
	public void setStore(Store store) {
		this.store = store;
	}
}
