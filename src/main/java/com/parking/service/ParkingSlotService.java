package com.parking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parking.dto.ParkingSlotRequest;
import com.parking.entity.ParkingSlot;
import com.parking.entity.ParkingSlot.SlotType;
import com.parking.repository.ParkingSlotRepository;

import java.util.List;

/* Service class for handling parking slot operations */
@Service
@RequiredArgsConstructor
@Slf4j
public class ParkingSlotService {
    
    private final ParkingSlotRepository slotRepository;
    
    
    @Transactional
    public ParkingSlot createSlot(ParkingSlotRequest request) {
        log.info("==============Creating parking slot: =========" + request.getSlotNumber());
        
        ParkingSlot slot = new ParkingSlot();
        slot.setSlotNumber(request.getSlotNumber());
        slot.setSlotType(request.getSlotType());
        slot.setAvailable(true);
        
        ParkingSlot saved = slotRepository.save(slot);
        
        log.info("Parking slot created with ID: " + saved.getId());
        
        return saved;
    }
    
    
    public List<ParkingSlot> getAllSlots(Boolean available, SlotType type) {
        log.debug("Fetching slots - available: " + available + ", type: " + type);
        
        // both filters present
        if (available != null && type != null) {
            return slotRepository.findAll().stream()
                    .filter(s -> s.isAvailable() == available && s.getSlotType().equals(type))
                    .toList();
        } 
        // only availability filter
        		else if (available != null) {
            return slotRepository.findByAvailable(available);
        } 
        // only type filter
        else if (type != null) {
            return slotRepository.findBySlotType(type);
        }
        
        // no filters, return all
        return slotRepository.findAll();
    }
    
}
