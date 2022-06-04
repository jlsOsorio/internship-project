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
import javax.persistence.SequenceGenerator;
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
@SequenceGenerator(name = "seqInvNumber", initialValue = 100000)
public class Invoice implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqInvNumber")
	@EqualsAndHashCode.Include
	private Long invoiceNumber;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	private Instant date;

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private Integer transactionType;

	@Setter(AccessLevel.NONE)
	private Double totalNoIva;

	@Setter(AccessLevel.NONE)
	private Double totalIva;

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
	 * @param date          invoice's date
	 * @param transactionType   invoice's transactionType
	 * @param user          user's id
	 * @param cashRegister  cash register's id
	 */
	public Invoice(Long invoiceNumber, Instant date, TransactionType transactionType, User user,
			CashRegister cashRegister) {
		super();
		this.invoiceNumber = invoiceNumber;
		this.date = date;
		setTransaction(transactionType);
		this.user = user;
		this.cashRegister = cashRegister;
		setTotalNoIva();
		setTotalIva();
	}

	/**
	 * Gets a transactionType.
	 * 
	 * @return transactionType
	 */
	public TransactionType getTransaction() {
		return TransactionType.valueOf(transactionType);
	}

	/**
	 * Sets transactionType.
	 * 
	 * @param transactionType transactionType type
	 */
	public void setTransaction(TransactionType transactionType) {
		if (transactionType != null) {
			this.transactionType = transactionType.getCode();
		}
	}

	/**
	 * Calculates the sum of all products IVA and returns it.
	 * 
	 * @return Total of products IVA
	 */
	public void setTotalIva() {
		Double sum = 0.0;
		for (InvoicedProduct product : invoicedProducts) {
			sum += product.getSubTotalIva();
		}

		this.totalIva = sum;
	}

	/**
	 * Calculates the sum of all products without IVA.
	 * 
	 * @return Total of products without IVA
	 */
	public void setTotalNoIva() {
		Double sum = 0.0;
		for (InvoicedProduct product : invoicedProducts) {
			sum += product.getSubTotalNoIva();
		}

		this.totalNoIva = sum;
	}

}
