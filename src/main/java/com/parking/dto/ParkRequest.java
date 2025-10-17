package com.parking.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ParkRequest {
	
	@NotBlank(message = "Vehicle Id is required")
	   private String vehicleId;
	
}
