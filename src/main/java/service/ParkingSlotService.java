package service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import repository.ParkingSlotRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dto.ParkingSlotRequest;
import entity.ParkingSlot;
import entity.SlotType;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParkingSlotService {
    
    private final ParkingSlotRepository slotRepository;
    
    @Transactional
    public ParkingSlot createSlot(ParkingSlotRequest request) {
        log.info("Creating parking slot: {}", request.getSlotNumber());
        
        ParkingSlot slot = new ParkingSlot();
        slot.setSlotNumber(request.getSlotNumber());
        slot.setSlotType(request.getSlotType());
        slot.setAvailable(true);
        
        ParkingSlot saved = slotRepository.save(slot);
        log.info("Parking slot created with ID: {}", saved.getId());
        return saved;
    }
    
    public List<ParkingSlot> getAllSlots(Boolean available, SlotType type) {
        log.debug("Fetching slots - available: {}, type: {}", available, type);
        
        if (available != null && type != null) {
            return slotRepository.findAll().stream()
            		.filter(s -> s.isAvailable() == available && s.getSlotType().equals(type))
                    .toList();
        } else if (available != null) {
            return slotRepository.findByIsAvailable(available);
        } else if (type != null) {
            return slotRepository.findBySlotType(type);
        }
        
        return slotRepository.findAll();
    }
}