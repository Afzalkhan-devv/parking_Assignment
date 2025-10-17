package entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Vehicle {

	@Id @GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	@Column(unique = true, nullable = false)
	private String licensePlate;
	
	@Column(nullable = false)
	private String ownername;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private VehicleType vehicleType;
	
	@CreationTimestamp
	private LocalDateTime registeredAt;
	
	public enum VehicleType {
        CAR, BIKE, TRUCK
    }
	
}
