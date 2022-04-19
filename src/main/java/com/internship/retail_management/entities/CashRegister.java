package com.internship.retail_management.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represents a cash register.
 * 
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 *
 */
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
@Table(name = "tb_cash_register")
public class CashRegister implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "store_id")
	private Store store;

	@Setter(AccessLevel.NONE)
	@JsonIgnore
	@OneToMany(mappedBy = "cashRegister")
	List<OperatingFund> operatingFunds = new ArrayList<>();

	/**
	 * Constructor that creates a cash register.
	 * 
	 * @param id Cash register's id
	 * @param store Store's id
	 */
	public CashRegister(Long id, Store store) {
		super();
		this.id = id;
		this.store = store;
	}
	
}
