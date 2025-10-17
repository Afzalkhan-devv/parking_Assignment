package service;

import dto.ParkRequest;
import entity.ParkingSlot;
import entity.ParkingTicket;
import entity.Vehicle;
import exception.BusinessException;
import repository.ParkingSlotRepository;
import repository.ParkingTicketRepository;
import repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParkingServiceTest {
    
    @Mock
    private VehicleRepository vehicleRepository;
    
    @Mock
    private ParkingSlotRepository slotRepository;
    
    @Mock
    private ParkingTicketRepository ticketRepository;
    
    @InjectMocks
    private ParkingService parkingService;
    
    @Test
    void parkVehicle_Success() {
        ParkRequest request = new ParkRequest();
        request.setVehicleId("vehicle-123");
        
        Vehicle vehicle = new Vehicle();
        vehicle.setId("vehicle-123");
        vehicle.setVehicleType(Vehicle.VehicleType.CAR);
        
        ParkingSlot slot = new ParkingSlot();
        slot.setId("slot-123");
        slot.setSlotType(ParkingSlot.SlotType.CAR);
        slot.setAvailable(true);
        
        when(vehicleRepository.findById("vehicle-123")).thenReturn(Optional.of(vehicle));
        when(ticketRepository.findByvehicleIdAndExitTimeIsNull(any())).thenReturn(Optional.empty());
        when(slotRepository.findFirstBySlotTypeAndIsAvailableTrue(any())).thenReturn(Optional.of(slot));
        when(ticketRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);
        
        ParkingTicket result = parkingService.parkVehicle(request);
        
        assertNotNull(result);
        assertFalse(slot.isAvailable());
        verify(ticketRepository).save(any());
    }
    
    @Test
    void parkVehicle_NoAvailableSlot_ThrowsException() {
        ParkRequest request = new ParkRequest();
        request.setVehicleId("vehicle-123");
        
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleType(Vehicle.VehicleType.CAR);
        
        when(vehicleRepository.findById(any())).thenReturn(Optional.of(vehicle));
        when(ticketRepository.findByvehicleIdAndExitTimeIsNull(any())).thenReturn(Optional.empty());
        when(slotRepository.findFirstBySlotTypeAndIsAvailableTrue(any())).thenReturn(Optional.empty());
        
        assertThrows(BusinessException.class, () -> parkingService.parkVehicle(request));
    }
}
