package com.parking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parking.dto.VehicleRequest;
import com.parking.entity.Vehicle;
import com.parking.exception.BusinessException;
import com.parking.exception.ResourceNotFoundException;
import com.parking.repository.VehicleRepository;

import java.util.List;

/* Service class to handle vehicle related operations */
@Service
public class VehicleService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(VehicleService.class); //logger statemnet explicit calling

    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }
    
    
    @Transactional
    public Vehicle registerVehicle(VehicleRequest request) {
        log.info("Registering vehicle with license plate: " + request.getLicensePlate());
        
        if (vehicleRepository.existsByLicensePlate(request.getLicensePlate())) {
            log.debug("::::::Vehicle already exists:::::" + request.getLicensePlate());
            throw new BusinessException("Vehicle withlicense plate already exists");
        }
        
        
        Vehicle vehicle = new Vehicle();
         vehicle.setLicensePlate(request.getLicensePlate());
          vehicle.setOwnerName(request.getOwnerName());
         vehicle.setVehicleType(request.getVehicleType());
        
        Vehicle saved = vehicleRepository.save(vehicle);
        log.info(":::AK:::Vehicle registered successfully with ID:::::AK " + saved.getId());
        
        return saved;
    }
    
    
    public Vehicle getVehicleById(String id) {
        log.debug("Fetching vehicle with ID: " + id);
        
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with ID: " + id));
    }
    
    
    public List<Vehicle> getAllVehicles() {
        log.debug("Fetching all vehicles from repository");
        
        return vehicleRepository.findAll();
    }
    
}
