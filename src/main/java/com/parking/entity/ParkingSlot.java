package com.parking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/* Entity representing a parking slot */
@Entity
@Data
public class ParkingSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false)
    private String slotNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SlotType slotType;

    @Column(nullable = false)
    private boolean available = true;

    // Slot type enum
    public enum SlotType {
        CAR, BIKE, TRUCK
    }
}
