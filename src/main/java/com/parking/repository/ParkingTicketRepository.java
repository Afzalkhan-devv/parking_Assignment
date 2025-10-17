package com.parking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parking.entity.ParkingTicket;

// Repository for handling ParkingTicket operations

public interface ParkingTicketRepository extends JpaRepository<ParkingTicket, String> {

    // find the active ticket
    Optional<ParkingTicket> findByvehicleIdAndExitTimeIsNull(String vehicleId); }
