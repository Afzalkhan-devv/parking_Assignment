package com.parking.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.*;
import lombok.Data;

/* Entity representing a vehicle */
@Entity
@Data
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false)
    private String licensePlate;

    @Column(nullable = false)
    private String ownerName;  // fixed camel case for clarity

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime registeredAt;

    // Helper method to display basic vehicle info
    public String info() {
        return "Vehicle[ID=" + id + ", Plate=" + licensePlate + ", Owner=" + ownerName + "]";
    }

    // Enum for vehicle types
    public enum VehicleType {
        CAR, BIKE, TRUCK
    }

}
