package com.internship.retail_management.entities;

import java.io.Serializable;
import java.time.Instant;
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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.internship.retail_management.entities.enums.TransactionType;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represents a invoice.
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
@Table(name = "tb_invoice")
public class Invoice implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long invoiceNumber;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	private Instant date;
	
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private Integer transaction;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "cash_register_id")
	private CashRegister cashRegister;
	
	@Setter(AccessLevel.NONE)
	@OneToMany(mappedBy = "invoice")
	List<InvoicedProduct> invoicedProducts = new ArrayList<>();

	/**
	 * Constructor that creates a invoice.
	 * 
	 * @param invoiceNumber invoice's id
	 * @param date invoice's date
	 * @param transaction invoice's transaction
	 * @param user user's id
	 * @param cashRegister cash register's id
	 */
	public Invoice(Long invoiceNumber, Instant date, TransactionType transaction, User user,
			CashRegister cashRegister) {
		super();
		this.invoiceNumber = invoiceNumber;
		this.date = date;
		setTransaction(transaction);
		this.user = user;
		this.cashRegister = cashRegister;
	}

	/**
	 * Gets a transaction.
	 * @return transaction
	 */
	public TransactionType getTransaction() {
		return TransactionType.valueOf(transaction);
	}

	/**
	 * Sets transaction.
	 * @param transaction transaction type
	 */
	public void setTransaction(TransactionType transaction) {
		if (transaction != null)
		{
			this.transaction = transaction.getCode();
		}
	}
	
	/**
	 * Calculates the sum of all products iva and returns it.
	 * @return Total of products iva
	 */
	public Double getTotalIva() {
		Double sum = 0.0;
		for (InvoicedProduct product : invoicedProducts)
		{
			sum += product.getSubTotalIva();
		}
		
		return sum;
	}
	
	/**
	 * Calculates the sum of all products without iva.
	 * @return Total of products without iva
	 */
	public Double getTotalNoIva() {
		Double sum = 0.0;
		for (InvoicedProduct product : invoicedProducts)
		{
			sum += product.getSubTotalNoIva();
		}
		
		return sum;
	}
	
}
