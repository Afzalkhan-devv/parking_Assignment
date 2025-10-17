package com.parking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.parking.repository")
@EntityScan("com.parking.entity") 
public class ParkingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkingSystemApplication.class, args);
	}

}


//Below is the testing sequence & responses
/*
1. POST /api/slots          → Create slots
2. POST /api/vehicles       → Register vehicle (save ID)
3. GET  /api/vehicles       → List all vehicles
4. POST /api/park           → Park vehicle (save ticket ID)
5. GET  /api/slots?available=true → Check available slots
6. GET  /api/tickets/{id}   → Get ticket details
7. POST /api/unpark/{id}    → Unpark vehicle
8. GET  /api/slots?available=true → Verify slot is free
*/
//vehicle id's for testing POST /park mapping
//0152c9ff-40a7-4e0e-866e-d3ffe71cb16a -vehicle ID test car
//bcf2c4d6-cad3-4409-a08c-b0f54b21bbce3 - Bike
//9d70ab6a-886b-414a-ba7e-472db042fd87 - truck

/*{
"id": "c3a138ea-6697-4920-8aef-df11cb61edb4",
"licensePlate": "HR51CB5999",
"ownername": "Afzal khan",
"vehicleType": "CAR",
"registeredAt": "2025-10-17T18:30:39.991276"
} */