package controller;

import dto.ParkRequest;
import entity.ParkingTicket;
import service.ParkingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ParkingController {
    
    private final ParkingService parkingService;
    
    @PostMapping("/park")
    public ResponseEntity<ParkingTicket> parkVehicle(@Valid @RequestBody ParkRequest request) {
        ParkingTicket ticket = parkingService.parkVehicle(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticket);
    }
    
    @PostMapping("/unpark/{ticketId}")
    public ResponseEntity<ParkingTicket> unparkVehicle(@PathVariable String ticketId) {
        ParkingTicket ticket = parkingService.unparkVehicle(ticketId);
        return ResponseEntity.ok(ticket);
    }
    
    @GetMapping("/tickets/{id}")
    public ResponseEntity<ParkingTicket> getTicket(@PathVariable String id) {
        ParkingTicket ticket = parkingService.getTicket(id);
        return ResponseEntity.ok(ticket);
    }
}
