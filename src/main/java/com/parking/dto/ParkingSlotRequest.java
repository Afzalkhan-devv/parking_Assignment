package com.parking.dto;

import com.parking.entity.ParkingSlot.SlotType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
  public class ParkingSlotRequest {
	@NotBlank(message = "Slot number is required")
	 private String slotNumber;
	
	  @NotNull(message = "Slot type is required")
	    private SlotType slotType;
}

//We can put messages in properties files also as standard way but due to time constraint i am putting directly