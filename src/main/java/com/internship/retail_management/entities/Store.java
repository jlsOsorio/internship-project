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

import com.internship.retail_management.entities.enums.StoreStatus;

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
@Table(name = "tb_store")
public class Store implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	private String address;
	private String council;
	private String zipCode;
	private String contact;
	
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private Integer status;
	
	@Setter(AccessLevel.NONE)
	@OneToMany(mappedBy = "store")
	private List<CashRegister> cashRegisters = new ArrayList<>();
	
	public Store(Long id, String address, String council, String zipCode, String contact, StoreStatus status) {
		this.id = id;
		this.address = address;
		this.council = council;
		this.zipCode = zipCode;
		this.contact = contact;
		setStatus(status);
	}
	

	public StoreStatus getStatus() {
		return StoreStatus.valueOf(status);
	}

	public void setStatus(StoreStatus status) {
		if (status != null)
		{
			this.status = status.getCode();
		}
	}
	
}
