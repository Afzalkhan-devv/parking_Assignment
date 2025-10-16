package entity;

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
public class ParkingSlot {
	
	@Id @ GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	@Column(unique = true, nullable = false)
	private String slotNumber;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private SlotType slotType;
	
	@Column(nullable = false)
	private boolean isAvailable = true;
	
	
}
