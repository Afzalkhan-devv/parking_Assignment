package repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import entity.ParkingSlot;
import entity.ParkingSlot.SlotType;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, String>{

	Optional<ParkingSlot> findFirstBySlotTypeAndIsAvailableTrue(SlotType slotType);
	List<ParkingSlot> findByIsAvailable (Boolean isAvailable);
	List<ParkingSlot> findBySlotType(SlotType slotType);
}
