package entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import lombok.Data;

@Data @Entity
public class ParkingTicket {

	private String id;
	private Vehicle vehicle;
	private ParkingSlot slot;
	private LocalDateTime entryTime;
	private LocalDateTime exitTime;
	
}
