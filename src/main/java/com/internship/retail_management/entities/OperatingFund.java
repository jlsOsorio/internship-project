package com.internship.retail_management.entities;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represents a operating fund.
 * 
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 */
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_operating_fund")
public class OperatingFund implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	/**
	 * Operating fund's id.
	 */
	private Long id;
	/**
	 * Operating fund's entry quantity.
	 */
	private Double entryQty;
	/**
	 * Operating fund's exit quantity.
	 */
	private Double exitQty;

	/**
	 * Operating fund's time stamp.
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	private Instant moment;

	/**
	 * Operating fund's associated user id.
	 */
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	/**
	 * Operating fund associated cash register id.
	 */
	@ManyToOne
	@JoinColumn(name = "cash_register_id")
	private CashRegister cashRegister;

}
