package repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import entity.ParkingTicket;

public interface ParkingTicketRepository extends JpaRepository<ParkingTicket, String>{

	Optional<ParkingTicket> findByvehicleIdAndExitTimeIsNull(String vehicleId);
}
