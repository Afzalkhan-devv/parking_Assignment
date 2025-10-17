package com.parking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parking.dto.*;
import com.parking.entity.*;
import com.parking.exception.*;
import com.parking.repository.*;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParkingService {
    
    private final VehicleRepository vehicleRepository;
    private final ParkingSlotRepository slotRepository;
    private final ParkingTicketRepository ticketRepository;
    
    
    @Transactional
    public ParkingTicket parkVehicle(ParkRequest request) {
        log.info("Entering  ParkingTicket parkVehicle: " );
        
        // Get vehicle
        		Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("vehicle not found"));
        
        // Check if already parked
        ticketRepository.findByvehicleIdAndExitTimeIsNull(vehicle.getId())
                .ifPresent(t -> {
                    throw new BusinessException("vehicle is already parked");
                });
        
        
        // Find available slot
        ParkingSlot.SlotType slotType = ParkingSlot.SlotType.valueOf(vehicle.getVehicleType().name());
        		ParkingSlot slot = slotRepository.findFirstBySlotTypeAndAvailableTrue(slotType)
                .orElseThrow(() -> new BusinessException("No available slots for " + vehicle.getVehicleType()));
        
        // Mark slot as occupied
         slot.setAvailable(false);
        slotRepository.save(slot);
        
        // Create ticket
        ParkingTicket ticket = new ParkingTicket();
        		ticket.setVehicle(vehicle);
        		ticket.setSlot(slot);
        
        ParkingTicket saved = ticketRepository.save(ticket);
        log.info("Vehicle parked successfully. Ticket ID: {}", saved.getId()); return saved;
    }
    
    
    @Transactional
    public ParkingTicket unparkVehicle(String ticketId) {
        log.info("Unparking vehicle with ticket ID: {}", ticketId);
        
        ParkingTicket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));
        
        
        if (ticket.getExitTime() != null) {
            throw new BusinessException("Vehicle already unparked");
        }
        
        
        // Set exit time
        ticket.setExitTime(LocalDateTime.now());
        
        // Free the slot
        ParkingSlot slot = ticket.getSlot();
        slot.setAvailable(true);
        slotRepository.save(slot);
        
        ParkingTicket saved = ticketRepository.save(ticket);
        log.info("Vehicle unparked successfully fo minutes = ", 
                java.time.Duration.between(ticket.getEntryTime(), ticket.getExitTime()).toMinutes());
        
        return saved;
    }
    
    
    public ParkingTicket getTicket(String ticketId) {
        log.debug("Fetching ticket with ID::::", ticketId);
        
        return ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("==Ticket not found=="));
    }
    
}
