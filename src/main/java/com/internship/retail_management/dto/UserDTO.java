package com.internship.retail_management.dto;

import java.time.Instant;

import com.internship.retail_management.entities.Store;
import com.internship.retail_management.entities.User;
import com.internship.retail_management.entities.enums.Category;
import com.internship.retail_management.entities.enums.Status;

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
	
	public UserDTO() {}

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Instant getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Instant birthDate) {
		this.birthDate = birthDate;
	}

	public Long getNif() {
		return nif;
	}

	public void setNif(Long nif) {
		this.nif = nif;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCouncil() {
		return council;
	}

	public void setCouncil(String council) {
		this.council = council;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}
}
