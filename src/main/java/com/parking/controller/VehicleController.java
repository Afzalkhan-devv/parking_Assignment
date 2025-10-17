package com.parking.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.parking.dto.VehicleRequest;
import com.parking.entity.Vehicle;
import com.parking.service.VehicleService;

import java.util.List;

/* This controller handles vehicle related APIs */

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
@Slf4j
public class VehicleController {
    
    private final VehicleService vehicleService;
    
    // register a new vehicle
    @PostMapping
    public ResponseEntity<Vehicle> registerVehicle(@Valid @RequestBody VehicleRequest request) {
        log.info("::::::::::::::::::Entering registerVehicle method=================");
        Vehicle vehicle = vehicleService.registerVehicle(request);
        log.debug("Vehicle registered with ID", vehicle.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicle);
    }
    
    // get vehicle details by ID
    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicle(@PathVariable String id) {
        log.info("Fetching vehicle details for ID: {}", id);
        Vehicle vehicle = vehicleService.getVehicleById(id);
        log.info("Vehicle fetched success===============");
        return ResponseEntity.ok(vehicle);
    }
    
    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        log.info("Fetching all vehicles");
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        return ResponseEntity.ok(vehicles);
    }
}
