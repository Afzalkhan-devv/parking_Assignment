package com.parking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parking.entity.ParkingSlot;
import com.parking.entity.ParkingSlot.SlotType;

// Repository to handle CRUD operations for ParkingSlot
public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, String> {

    // find the first available slot of a given type
    Optional<ParkingSlot> findFirstBySlotTypeAndAvailableTrue(SlotType slotType);

    /*find slots by availability */
     List<ParkingSlot> findByAvailable(Boolean available);

    //find slots by type
    List<ParkingSlot> findBySlotType(SlotType slotType);
}
