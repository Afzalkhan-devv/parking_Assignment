package service;

import repository.VehicleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dto.VehicleRequest;
import entity.Vehicle;
import exception.BusinessException;
import exception.ResourceNotFoundException;

import java.util.List;

@Service
public class VehicleService {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(VehicleService.class);

    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }
    
    @Transactional
    public Vehicle registerVehicle(VehicleRequest request) {
        log.info("Registering vehicle with license plate: {}", request.getLicensePlate());
        
        if (vehicleRepository.existsByLicensePlate(request.getLicensePlate())) {
            log.error("Vehicle already exists: {}", request.getLicensePlate());
            throw new BusinessException("Vehicle with this license plate already exists");
        }
        
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate(request.getLicensePlate());
        vehicle.setOwnername(request.getOwnerName());
        vehicle.setVehicleType(request.getVehicleType());
        
        Vehicle saved = vehicleRepository.save(vehicle);
        log.info("Vehicle registered successfully with ID: {}", saved.getId());
        return saved;
    }
    
    public Vehicle getVehicleById(String id) {
        log.debug("Fetching vehicle with ID: {}", id);
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with ID: " + id));
    }
    
    public List<Vehicle> getAllVehicles() {
        log.debug("Fetching all vehicles");
        return vehicleRepository.findAll();
    }
}