package com.internship.retail_management.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.internship.retail_management.entities.enums.IvaValues;
import com.internship.retail_management.services.Tax;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represents IVA.
 * 
 * @author Bruno Soares
 * @author João Osório
 * @version 1.0
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "tb_iva")
public class Iva implements Tax, Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private Integer value;
	
	@Setter(AccessLevel.NONE)
	@JsonIgnore //para não estar em loop no momento do pedido (um order tem um user que tem varios orders que têm um pedido, ...)
	@OneToMany(mappedBy = "ivaValue")
	List<Product> products = new ArrayList<>();
	
	/**
	 * Constructor that creates a IVA entry.
	 * @param id IVA's id
	 * @param value IVA'S value
	 */
	public Iva(Long id, IvaValues value) {
		super();
		this.id = id;
		setValue(value);
	}
	
	/**
	 * Gets IVA value.
	 * @return value of IVA
	 */
	public IvaValues getValue() {
		return IvaValues.valueOf(value);
	}

	/**
	 * Sets IVA value.
	 * @param value
	 */
	public void setValue(IvaValues value) {
		if (value != null)
		{
			this.value = value.getCode();
		}
	}
	
	/**
	 * Gets tax with the IVA value and divides it by 100 to get a double.
	 * @return tax 
	 */
	@Override
	public Double getTax() {
		return Double.valueOf(getValue().getCode()) / 100;
	}

}
