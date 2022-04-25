package com.internship.retail_management.dto;

import com.internship.retail_management.entities.StockMovement;
import com.internship.retail_management.entities.enums.Movement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockMovementInsertDTO {

	private Integer quantity;
	private Movement movement;

	public StockMovementInsertDTO(StockMovement entity) {
		this.quantity = entity.getQuantity();
		this.movement = entity.getMovement();
	}

}
