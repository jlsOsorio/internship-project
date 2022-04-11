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

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
@Table(name = "tb_iva")
public class Iva implements Tax, Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private Integer value;
	
	@Setter(AccessLevel.NONE)
	@JsonIgnore //para não estar em loop no momento do pedido (um order tem um user que tem varios orders que têm um pedido, ...)
	@OneToMany(mappedBy = "ivaValue")
	List<Product> products = new ArrayList<>();
	
	public Iva(Long id, IvaValues value) {
		super();
		this.id = id;
		setValue(value);
	}
	
	public IvaValues getValue() {
		return IvaValues.valueOf(value);
	}

	public void setValue(IvaValues value) {
		if (value != null)
		{
			this.value = value.getCode();
		}
	}
	
	@Override
	public Double getTax() {
		return Double.valueOf(getValue().getCode()) / 100;
	}

}
