package com.internship.retail_management.dto;

import java.time.Instant;

import com.internship.retail_management.entities.CashRegister;
import com.internship.retail_management.entities.OperatingFund;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OperatingFundInsertDTO {

	private Double entryQty;
	private Double exitQty;
	private CashRegister cashRegister;
	private Instant moment;
	
	public OperatingFundInsertDTO(OperatingFund entity) {
		this.entryQty = entity.getEntryQty();
		this.exitQty = entity.getExitQty();
		this.cashRegister = entity.getCashRegister();
		this.moment = entity.getMoment();
	}
}
