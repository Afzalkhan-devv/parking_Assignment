package repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import entity.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, String>{
	Optional<Vehicle> findByLicensePlate(String LicensePlate);
	boolean existsByLicensePlate(String LicensePlate);
}
