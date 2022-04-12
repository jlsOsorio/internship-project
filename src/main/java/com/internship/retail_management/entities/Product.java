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

	public void updateStock(Integer qty, Movement movType) {

		if (movType == Movement.IN)
		{
			stock += qty;
			stockMovements.add(new StockMovement(null, qty, Movement.IN, this));
		}
		else
		{
			stock -= qty;
			stockMovements.add(new StockMovement(null, qty, Movement.OUT, this));	
		}


	}

	public void updateInvoicedStock() {
		
		if (invoicedProducts.size() != 0)
		{
			for (InvoicedProduct movement : invoicedProducts)
			{
				if (movement.getStockMovement().getMovement() == Movement.IN && movement.getProduct().equals(this))
				{
					stock += movement.getQuantity();
					stockMovements.add(new StockMovement(null, movement.getQuantity(), Movement.IN, this));	
				}
				
				if (movement.getStockMovement().getMovement() == Movement.OUT && movement.getProduct().equals(this))
				{
					stock -= movement.getQuantity();
					stockMovements.add(new StockMovement(null, movement.getQuantity(), Movement.OUT, this));	
				}
			}
		}
	}
}
