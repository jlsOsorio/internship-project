package com.internship.retail_management.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.internship.retail_management.entities.enums.Movement;
import com.internship.retail_management.entities.enums.TransactionType;

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
@Table(name = "tb_invoiced_product")
public class InvoicedProduct implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	private Integer quantity;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	@Setter(AccessLevel.NONE)
	@OneToOne
	@MapsId //Na classe dependente
	private StockMovement stockMovement;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "invoice_id")
	private Invoice invoice;
	
	public InvoicedProduct(Long id, Integer quantity, Product product, Invoice invoice) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.product = product;
		this.invoice = invoice;
		setStockMovement();
	}
	
	public void setStockMovement() {
		if (invoice.getTransaction() == TransactionType.DEBIT)
		{
			this.stockMovement = new StockMovement(null, quantity, Movement.OUT, product);
			product.getInvoicedProducts().add(this);
		}
		
		if (invoice.getTransaction() == TransactionType.CREDIT)
		{
			this.stockMovement = new StockMovement(null, quantity, Movement.IN, product);
			product.getInvoicedProducts().add(this);
		}
	}
	
	//Necessário meter o "get" para que o valor seja mostrado na execução do controlador (particularidade do Java EE)
	public Double getSubTotalIva() {
		return quantity * (product.getGrossPrice() + product.getTaxedPrice());
	}

	public Double getSubTotalNoIva() {
		return quantity * (product.getGrossPrice());
	}
}
