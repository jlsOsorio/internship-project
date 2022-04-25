package com.internship.retail_management.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.internship.retail_management.entities.enums.Category;
import com.internship.retail_management.entities.enums.Status;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represents a user.
 * 
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 */
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
@Table(name = "tb_user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	private String name;

	@EqualsAndHashCode.Include
	private String email;
	private String password;
	private String phone;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT")
	private Instant birthDate;

	@EqualsAndHashCode.Include
	private Long nif;

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private Integer category;

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private Integer status;

	private String address;
	private String council;
	private String zipCode;

	@ManyToOne
	@JoinColumn(name = "store_id")
	private Store store;

	@Setter(AccessLevel.NONE)
	@OneToMany(mappedBy = "user")
	List<OperatingFund> operatingFunds = new ArrayList<>();

	@JsonIgnore
	@Setter(AccessLevel.NONE)
	@OneToMany(mappedBy = "user")
	Set<Invoice> invoices = new HashSet<>();

	/**
	 * Constructor that creates a user.
	 * 
	 * @param id        User's id
	 * @param name      User's name
	 * @param email     User's email
	 * @param password  User's password
	 * @param phone     User's phone number
	 * @param birthDate User's birth date
	 * @param nif       User's tax identification number
	 * @param category  User's category either EMPLOYEE or SUPERVISOR
	 * @param status    User's status either ACTIVE or INACTIVE
	 * @param address   User's address
	 * @param council   User's council
	 * @param zipCode   User's zip code
	 * @param store     User's store id
	 */
	public User(Long id, String name, String email, String password, String phone, Instant birthDate, Long nif,
			Category category, Status status, String address, String council, String zipCode, Store store) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.birthDate = birthDate;
		this.nif = nif;
		setCategory(category);
		setStatus(status);
		this.address = address;
		this.council = council;
		this.zipCode = zipCode;
		this.store = store;
	}

	/**
	 * Retrieves the status for the user.
	 * 
	 * @return
	 */
	public Status getStatus() {
		return Status.valueOf(status);
	}

	/**
	 * Sets the status for the user.
	 * 
	 * @param status
	 */
	public void setStatus(Status status) {
		if (status != null) {
			this.status = status.getCode();
		}
	}

	/**
	 * Retrieves the category for the user.
	 * 
	 * @return
	 */
	public Category getCategory() {
		return Category.valueOf(category);
	}

	/**
	 * Sets the category for the user.
	 * 
	 * @param category
	 */
	public void setCategory(Category category) {
		if (category != null) {
			this.category = category.getCode();
		}
	}

}
