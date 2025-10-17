package controller;

import dto.ParkingSlotRequest;
import entity.ParkingSlot;
import entity.ParkingSlot.SlotType;
import service.ParkingSlotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/slots")
@RequiredArgsConstructor
public class ParkingSlotController {
    
    private final ParkingSlotService slotService;
    
    @PostMapping
    public ResponseEntity<ParkingSlot> createSlot(@Valid @RequestBody ParkingSlotRequest request) {
        ParkingSlot slot = slotService.createSlot(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(slot);
    }
    
    @GetMapping
    public ResponseEntity<List<ParkingSlot>> getAllSlots(
            @RequestParam(required = false) Boolean available,
            @RequestParam(required = false) SlotType type) {
        List<ParkingSlot> slots = slotService.getAllSlots(available, type);
        return ResponseEntity.ok(slots);
    }
}
