package com.parking.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.parking.dto.ParkingSlotRequest;
import com.parking.entity.ParkingSlot;
import com.parking.entity.ParkingSlot.SlotType;
import com.parking.service.ParkingSlotService;

import java.util.List;

/* this class handles all parking slot related APIs */

@RestController
@RequestMapping("/api/slots")
@RequiredArgsConstructor
@Slf4j
public class ParkingSlotController {
    
    private final ParkingSlotService slotService;
    
    // create a new parking slot
    @PostMapping
    public ResponseEntity<ParkingSlot> createSlot(@Valid @RequestBody ParkingSlotRequest request) {
        log.info("==========Entering createSlot method=================");
        ParkingSlot slot = slotService.createSlot(request);
        log.info("Parking slot created with ID:::::", slot.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(slot);
    }
    
    // get all parking slots, can filter by availability and type
    @GetMapping
    public ResponseEntity<List<ParkingSlot>> getAllSlots(
            @RequestParam(required = false) Boolean available,
            @RequestParam(required = false) SlotType type) {
        log.info("Entering getAllSlots method. Filter ===========available:, type:", available, type);
        List<ParkingSlot> slots = slotService.getAllSlots(available, type);
        log.info("total slots fetched::::", slots.size());
        return ResponseEntity.ok(slots);
    }
}
