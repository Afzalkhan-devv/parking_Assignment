package com.parking.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.*;

import lombok.Data;

/* ==========Entity representing a parking ticket=========== */
@Entity
@Data
public class ParkingTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    // Vehicle associated with this ticket
    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    // Parking slot assigned for ticket
    @ManyToOne
    @JoinColumn(name = "slot_id", nullable = false)
    private ParkingSlot slot;

    // entered time the parking
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime entryTime;

    // Time when vehicle do exit-----
    private LocalDateTime exitTime;

}
