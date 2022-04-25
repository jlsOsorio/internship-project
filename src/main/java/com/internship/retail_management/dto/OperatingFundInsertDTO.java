package com.internship.retail_management.dto;

import java.time.Instant;

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
	private Long cashRegisterId;
	private Instant moment;

	public OperatingFundInsertDTO(OperatingFund entity) {
		this.entryQty = entity.getEntryQty();
		this.exitQty = entity.getExitQty();
		this.cashRegisterId = entity.getCashRegister().getId();
		this.moment = entity.getMoment();
	}
}
