package com.internship.retail_management.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
@Table(name = "tb_product")
public class Product implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	private String name;
	private Integer stock;
	private Double grossPrice;
	
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private Double taxedPrice;
	
	@ManyToOne
	@JoinColumn(name = "iva_id")
	private Iva ivaValue;
	
	public Product(Long id, String name, Integer stock, Double grossPrice, Iva ivaValue) {
		super();
		this.id = id;
		this.name = name;
		this.stock = stock;
		this.grossPrice = grossPrice;
		this.ivaValue = ivaValue;
		setTaxedPrice(ivaValue);
	}

	public Double getTaxedPrice() {
		return getIvaValue().getTax() * grossPrice;
	}

	public void setTaxedPrice(Iva iva) {
		this.taxedPrice = iva.getTax() * grossPrice;
	}
	
}
