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

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represents a invoiced product.
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
@Table(name = "tb_invoiced_product")
public class InvoicedProduct implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	private Integer quantity;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "invoice_id")
	private Invoice invoice;

	@Setter(AccessLevel.NONE)
	private Double subTotalIva;

	@Setter(AccessLevel.NONE)
	private Double subTotalNoIva;

	/**
	 * Constructor that creates a invoiced product.
	 * 
	 * @param id       invoiced product's id
	 * @param quantity product's quantity
	 * @param product  product's id
	 * @param invoice  invoice's id
	 */
	public InvoicedProduct(Long id, Integer quantity, Product product, Invoice invoice) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.product = product;
		this.invoice = invoice;
		this.product.getInvoicedProducts().add(this);
		setSubTotalNoIva();
		setSubTotalIva();
	}

	/**
	 * Calculates the IVA sub total by adding the product's gross price and the
	 * taxed price and multiplying for the quantity of the product.
	 * 
	 * @return Sub total with IVA
	 */
	public void setSubTotalIva() {
		this.subTotalIva = quantity * (product.getGrossPrice() + product.getTaxedPrice());
	}

	/**
	 * Calculates the sub total without IVA by multiplying the product's gross price
	 * with the amount of said product.
	 * 
	 * @return Sub total without IVA
	 */
	public void setSubTotalNoIva() {
		this.subTotalNoIva = quantity * (product.getGrossPrice());
	}
}
