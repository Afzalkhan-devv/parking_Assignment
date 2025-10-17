package com.parking.dto;

import com.parking.entity.Vehicle.VehicleType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VehicleRequest {

    	@NotBlank(message = "License plate is required")
    private String licensePlate;
    		
    @NotBlank(message = "Owner name is required")
        private String ownerName;
    		
    @NotNull(message = "Vehicle type is required")
     private VehicleType vehicleType;
    
}

//We can put messages in properties files also as standard way but due to time constraint i am putting directly