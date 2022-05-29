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
import com.internship.retail_management.entities.enums.Movement;
import com.internship.retail_management.entities.enums.TransactionType;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represents a product.
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
@Table(name = "tb_product")
public class Product implements Serializable {

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

	@Setter(AccessLevel.NONE)
	@OneToMany(mappedBy = "product")
	private List<StockMovement> stockMovements = new ArrayList<>();

	@JsonIgnore
	@Setter(AccessLevel.NONE)
	@OneToMany(mappedBy = "product")
	private List<InvoicedProduct> invoicedProducts = new ArrayList<>();

	/**
	 * Constructor for a product.
	 * 
	 * @param id         product's id
	 * @param name       product's name
	 * @param stock      product's total stock
	 * @param grossPrice product's gross price
	 * @param ivaValue   product's IVA value
	 */
	public Product(Long id, String name, Integer stock, Double grossPrice, Iva ivaValue) {
		super();
		this.id = id;
		this.name = name;
		this.stock = stock;
		this.grossPrice = grossPrice;
		this.ivaValue = ivaValue;
		setTaxedPrice(ivaValue);
	}

	public Product(Product product) {
		this.setId(null);
		this.setName(product.getName());
		this.setStock(product.getStock());
		this.setIvaValue(product.getIvaValue());
		this.setGrossPrice(product.getGrossPrice());
		this.setTaxedPrice(product.ivaValue);
	}
	
	/**
	 * Retrieves the taxed price by multiplying the IVA value with the gross price.
	 * 
	 * @return taxed price
	 */
	public Double getTaxedPrice() {
		return getIvaValue().getTax() * grossPrice;
	}

	/**
	 * Sets taxed product's price.
	 * 
	 * @param iva product's IVA
	 */
	public void setTaxedPrice(Iva iva) {
		if (iva != null) {
			this.taxedPrice = iva.getTax() * grossPrice;
		}
	}

	/**
	 * Updates the product stock based on the type of the transaction Either adds
	 * stock or removes stock.
	 * 
	 * @param qty     quantity of stock getting moved
	 * @param movType type of the movement IN or OUT
	 */
	public void updateStock(Integer qty, Movement movType) {

		if (movType == Movement.IN) {
			stock += qty;
			stockMovements.add(new StockMovement(null, qty, Movement.IN, this));
		} else {
			stock -= qty;
			stockMovements.add(new StockMovement(null, qty, Movement.OUT, this));
		}
	}

	/**
	 * Updates the invoiced stock based on the the type of movement either IN or
	 * OUT.
	 */
	public void updateInvoicedStock() {

		if (invoicedProducts.size() != 0) {
			InvoicedProduct invoiceProduct = invoicedProducts.get(invoicedProducts.size() - 1);
			if (invoiceProduct.getInvoice().getTransaction() == TransactionType.CREDIT) {
				stock += invoiceProduct.getQuantity();
				stockMovements.add(new StockMovement(null, invoiceProduct.getQuantity(), Movement.IN, this));
			}

			if (invoiceProduct.getInvoice().getTransaction() == TransactionType.DEBIT) {
				stock -= invoiceProduct.getQuantity();
				stockMovements.add(new StockMovement(null, invoiceProduct.getQuantity(), Movement.OUT, this));
			}
		}
	}
	
}
