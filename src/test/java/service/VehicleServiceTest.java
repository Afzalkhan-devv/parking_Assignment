package service;

import dto.VehicleRequest;
import entity.Vehicle;
import exception.BusinessException;
import repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {
    
    @Mock
    private VehicleRepository vehicleRepository;
    
    @InjectMocks
    private VehicleService vehicleService;
    
    @Test
    void registerVehicle_Success() {
        VehicleRequest request = new VehicleRequest();
        request.setLicensePlate("HR51CB5999");
        request.setOwnerName("Afzal khan");
        request.setVehicleType(Vehicle.VehicleType.CAR);
        
        when(vehicleRepository.existsByLicensePlate(anyString())).thenReturn(false);
        when(vehicleRepository.save(any(Vehicle.class))).thenAnswer(i -> i.getArguments()[0]);
        
        Vehicle result = vehicleService.registerVehicle(request);
        
        assertNotNull(result);
        assertEquals("HR51CB5699", result.getLicensePlate());
        verify(vehicleRepository).save(any(Vehicle.class));
    }
    
    @Test
    void registerVehicle_DuplicateLicense_ThrowsException() {
        VehicleRequest request = new VehicleRequest();
        request.setLicensePlate("HR51CB5999");
        
        when(vehicleRepository.existsByLicensePlate("HR51CB5999")).thenReturn(true);
        
        assertThrows(BusinessException.class, () -> vehicleService.registerVehicle(request));
        verify(vehicleRepository, never()).save(any());
    }
}
