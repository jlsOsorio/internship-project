package com.internship.retail_management.dto;

import java.time.Instant;

import com.internship.retail_management.entities.Store;
import com.internship.retail_management.entities.User;
import com.internship.retail_management.entities.enums.Category;
import com.internship.retail_management.entities.enums.Status;

/**
 * Data transfer object class for inserting a user.
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 *
 */
public class UserInsertDTO {

	private String name;
	private String email;
	private String phone;
	private String password;
	private Instant birthDate;
	private Long nif;
	private Category category;
	private Status status;
	private String address;
	private String council;
	private String zipCode;
	private Store store;
	
	/**
	 * Constructor with no arguments for the user insert DTO.
	 */
	public UserInsertDTO() {}

	/**
	 * Constructor for the user's insert DTO.
	 * @param name user's name
	 * @param email user's email
	 * @param password user's password
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
	public UserInsertDTO(String name, String email, String password, String phone, Instant birthDate, Long nif, Category category,
			Status status, String address, String council, String zipCode, Store store) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
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
	 * Retrieves user's insert.
	 * @param entity
	 */
	public UserInsertDTO(User entity) {
		super();
		this.name = entity.getName();
		this.email = entity.getEmail();
		this.password = entity.getPassword();
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
	 * Getter for user's insert name.
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for user's insert name.
	 * @param name user's insert name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for user's insert email.
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter for user's insert email.
	 * @param email user's insert email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Getter for user's insert password.
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter for user's insert password.
	 * @param password user's insert password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Getter for user's insert phone number.
	 * @return
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Setter for user's insert phone number.
	 * @param phone user's insert phone number
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Getter for user's insert birth date.
	 * @return
	 */
	public Instant getBirthDate() {
		return birthDate;
	}

	/**
	 * Setter for user's insert birth date.
	 * @param birthDate user's birth date
	 */
	public void setBirthDate(Instant birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * Getter for user's insert tax identification number.
	 * @return
	 */
	public Long getNif() {
		return nif;
	}

	/**
	 * Setter for user's insert tax identification number.
	 * @param nif user's insert tax identification number
	 */
	public void setNif(Long nif) {
		this.nif = nif;
	}

	/**
	 * Getter for user's insert category.
	 * @return
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * Setter for user's insert category.
	 * @param category user's insert category
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * Getter for user's insert status.
	 * @return
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Setter for user's insert status.
	 * @param status user's insert status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * Getter for user's insert address.
	 * @return
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Setter for user's insert address.
	 * @param address user's insert address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Getter for user's insert council.
	 * @return
	 */
	public String getCouncil() {
		return council;
	}

	/**
	 * Setter for user's insert council.
	 * @param council user's insert council
	 */
	public void setCouncil(String council) {
		this.council = council;
	}

	/**
	 * Getter for user's insert zip code.
	 * @return
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * Setter for user's insert zip code.
	 * @param zipCode user's insert zip code
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * Getter for user's insert store.
	 * @return
	 */
	public Store getStore() {
		return store;
	}

	/**
	 * Setter for user's insert store.
	 * @param store user's insert store
	 */
	public void setStore(Store store) {
		this.store = store;
	}
}
