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

	public Invoice(Long invoiceNumber, Instant date, TransactionType transaction, User user,
			CashRegister cashRegister) {
		super();
		this.invoiceNumber = invoiceNumber;
		this.date = date;
		setTransaction(transaction);
		this.user = user;
		this.cashRegister = cashRegister;
	}

	public TransactionType getTransaction() {
		return TransactionType.valueOf(transaction);
	}

	public void setTransaction(TransactionType transaction) {
		if (transaction != null)
		{
			this.transaction = transaction.getCode();
		}
	}
	
	public Double getTotalIva() {
		Double sum = 0.0;
		for (InvoicedProduct product : invoicedProducts)
		{
			sum += product.getSubTotalIva();
		}
		
		return sum;
	}
	
	public Double getTotalNoIva() {
		Double sum = 0.0;
		for (InvoicedProduct product : invoicedProducts)
		{
			sum += product.getSubTotalNoIva();
		}
		
		return sum;
	}
	
}
