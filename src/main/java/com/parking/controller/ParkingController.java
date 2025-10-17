package com.parking.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.parking.dto.ParkRequest;
import com.parking.entity.ParkingTicket;
import com.parking.service.ParkingService;

/*this class is responsible to parking api's */

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ParkingController {
    
    private final ParkingService parkingService;
    
    // park a vehicle
    @PostMapping("/park")
    public ResponseEntity<ParkingTicket> parkVehicle(@Valid @RequestBody ParkRequest request) {
        log.info("==========Entering /park method=================" );
        ParkingTicket ticket = parkingService.parkVehicle(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticket);
    }
    
    // unpark a vehicle by ticket id
    @PostMapping("/unpark/{ticketId}")
    public ResponseEntity<ParkingTicket> unparkVehicle(@PathVariable String ticketId) {
        log.info("Entering /unpark method=================" );
        ParkingTicket ticket = parkingService.unparkVehicle(ticketId);
        return ResponseEntity.ok(ticket);
    }
    
    //get ticket details
    @GetMapping("/tickets/{id}")
    public ResponseEntity<ParkingTicket> getTicket(@PathVariable String id) {
    	log.info("Entering ticket detailmethod ::::::=======" );
        ParkingTicket ticket = parkingService.getTicket(id);
        return ResponseEntity.ok(ticket);
    }
}
