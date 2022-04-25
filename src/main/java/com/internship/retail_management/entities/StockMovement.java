package com.internship.retail_management.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.internship.retail_management.entities.enums.Movement;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represents a stock movement.
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
@Table(name = "tb_stock_movement")
public class StockMovement implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	private Integer quantity;

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private Integer movement;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	/**
	 * Constructor for stock movements.
	 * 
	 * @param id       stock movement's id
	 * @param quantity quantity of the product
	 * @param movement type of stock movement either IN or OUT
	 * @param product  product's id
	 */
	public StockMovement(Long id, Integer quantity, Movement movement, Product product) {
		super();
		this.id = id;
		this.quantity = quantity;
		setMovement(movement);
		this.product = product;
	}

	/**
	 * Retrieves a movement.
	 * 
	 * @return
	 */
	public Movement getMovement() {
		return Movement.valueOf(movement);
	}

	/**
	 * Sets movement.
	 * 
	 * @param movement
	 */
	public void setMovement(Movement movement) {
		if (movement != null) {
			this.movement = movement.getCode();
		}
	}

}
