package entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data @Entity
public class ParkingTicket {

	
	@Id @GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	@JoinColumn(name = "vehicle_id", nullable=false ) 
	@ManyToOne
	private Vehicle vehicle;
	
	@ManyToOne
	@JoinColumn(name = "slot_id", nullable = false)
	private ParkingSlot slot;
	
	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime entryTime;
	private LocalDateTime exitTime;
	
}
