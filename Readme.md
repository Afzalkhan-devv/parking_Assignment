# Parking Management System

A RESTful API-based parking management system built with Spring Boot.

## Features

- Vehicle registration and management
- Parking slot management
- Park and unpark operations
- Automatic slot assignment based on vehicle type
- Parking ticket generation and tracking

## Tech Stack

- Java 17
- Spring Boot 3.4.x
- Spring Data JPA
- H2 In-Memory Database
- Lombok
- Maven

## Prerequisites

- JDK 17 or higher
- Maven 3.6+

## Setup and Run

1. **Clone the repository**
```bash
git clone <your-repo-url>
cd parking-system
```

2. **Build the project**
```bash
mvn clean install
```

3. **Run the application**
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

4. **Access H2 Console** (Optional)
```
URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:parkingdb
Username: sa
Password: (leave blank)
```

## API Endpoints

### Vehicle Management

#### Register a Vehicle
```http
POST /api/vehicles
Content-Type: application/json

{
  "licensePlate": "MH01AB1234",
  "ownerName": "John Doe",
  "vehicleType": "CAR"
}
```

**Response (201 Created):**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "licensePlate": "MH01AB1234",
  "ownerName": "John Doe",
  "vehicleType": "CAR",
  "registeredAt": "2024-10-16T10:30:00"
}
```

#### Get Vehicle by ID
```http
GET /api/vehicles/{id}
```

#### Get All Vehicles
```http
GET /api/vehicles
```

---

### Parking Slot Management

#### Create a Parking Slot
```http
POST /api/slots
Content-Type: application/json

{
  "slotNumber": "A-101",
  "slotType": "CAR"
}
```

**Response (201 Created):**
```json
{
  "id": "660e8400-e29b-41d4-a716-446655440000",
  "slotNumber": "A-101",
  "slotType": "CAR",
  "isAvailable": true
}
```

#### Get All Slots (with filters)
```http
GET /api/slots
GET /api/slots?available=true
GET /api/slots?type=CAR
GET /api/slots?available=true&type=BIKE
```

---

### Parking Operations

#### Park a Vehicle
```http
POST /api/park
Content-Type: application/json

{
  "vehicleId": "550e8400-e29b-41d4-a716-446655440000"
}
```

**Response (201 Created):**
```json
{
  "id": "770e8400-e29b-41d4-a716-446655440000",
  "vehicle": {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "licensePlate": "MH01AB1234",
    "ownerName": "John Doe",
    "vehicleType": "CAR"
  },
  "slot": {
    "id": "660e8400-e29b-41d4-a716-446655440000",
    "slotNumber": "A-101",
    "slotType": "CAR",
    "isAvailable": false
  },
  "entryTime": "2024-10-16T11:00:00",
  "exitTime": null
}
```

#### Unpark a Vehicle
```http
POST /api/unpark/{ticketId}
```

**Response (200 OK):**
```json
{
  "id": "770e8400-e29b-41d4-a716-446655440000",
  "vehicle": {...},
  "slot": {
    "id": "660e8400-e29b-41d4-a716-446655440000",
    "slotNumber": "A-101",
    "slotType": "CAR",
    "isAvailable": true
  },
  "entryTime": "2024-10-16T11:00:00",
  "exitTime": "2024-10-16T13:30:00"
}
```

#### Get Ticket Details
```http
GET /api/tickets/{ticketId}
```

---

## Error Responses

### Validation Error (400 Bad Request)
```json
{
  "timestamp": "2024-10-16T10:30:00",
  "status": 400,
  "errors": {
    "licensePlate": "License plate is required",
    "vehicleType": "Vehicle type is required"
  }
}
```

### Business Logic Error (400 Bad Request)
```json
{
  "timestamp": "2024-10-16T10:30:00",
  "status": 400,
  "message": "Vehicle with this license plate already exists"
}
```

### Resource Not Found (404 Not Found)
```json
{
  "timestamp": "2024-10-16T10:30:00",
  "status": 404,
  "message": "Vehicle not found with ID: 123"
}
```

---

## Testing Flow

### 1. Setup Initial Data

**Create Parking Slots:**
```bash
# CAR slots
curl -X POST http://localhost:8080/api/slots \
  -H "Content-Type: application/json" \
  -d '{"slotNumber":"A-101","slotType":"CAR"}'

curl -X POST http://localhost:8080/api/slots \
  -H "Content-Type: application/json" \
  -d '{"slotNumber":"A-102","slotType":"CAR"}'

# BIKE slots
curl -X POST http://localhost:8080/api/slots \
  -H "Content-Type: application/json" \
  -d '{"slotNumber":"B-101","slotType":"BIKE"}'

# TRUCK slot
curl -X POST http://localhost:8080/api/slots \
  -H "Content-Type: application/json" \
  -d '{"slotNumber":"C-101","slotType":"TRUCK"}'
```

**Register Vehicles:**
```bash
# Car
curl -X POST http://localhost:8080/api/vehicles \
  -H "Content-Type: application/json" \
  -d '{"licensePlate":"MH01AB1234","ownerName":"John Doe","vehicleType":"CAR"}'

# Bike
curl -X POST http://localhost:8080/api/vehicles \
  -H "Content-Type: application/json" \
  -d '{"licensePlate":"MH02CD5678","ownerName":"Jane Smith","vehicleType":"BIKE"}'
```

### 2. Test Parking Operations

**Park a vehicle** (save the vehicle ID from registration):
```bash
curl -X POST http://localhost:8080/api/park \
  -H "Content-Type: application/json" \
  -d '{"vehicleId":"<VEHICLE_ID>"}'
```

**Check available slots:**
```bash
curl http://localhost:8080/api/slots?available=true
```

**Unpark a vehicle** (use ticket ID from park response):
```bash
curl -X POST http://localhost:8080/api/unpark/<TICKET_ID>
```

### 3. Test Error Scenarios

**Duplicate license plate:**
```bash
curl -X POST http://localhost:8080/api/vehicles \
  -H "Content-Type: application/json" \
  -d '{"licensePlate":"MH01AB1234","ownerName":"Another Person","vehicleType":"CAR"}'
```

**Park already parked vehicle:**
```bash
# Park same vehicle twice
curl -X POST http://localhost:8080/api/park \
  -H "Content-Type: application/json" \
  -d '{"vehicleId":"<SAME_VEHICLE_ID>"}'
```

**No available slots:**
```bash
# Park vehicles until all CAR slots are full
# Then try to park another CAR
```

---

## Running Tests
```bash
# Run all tests
mvn test

# Run with coverage
mvn clean test jacoco:report

# Run specific test class
mvn test -Dtest=VehicleServiceTest
```

---

## Project Structure
```
src/main/java/com/parking/
├── ParkingSystemApplication.java
├── controller/
│   ├── VehicleController.java
│   ├── ParkingSlotController.java
│   └── ParkingController.java
├── service/
│   ├── VehicleService.java
│   ├── ParkingSlotService.java
│   └── ParkingService.java
├── repository/
│   ├── VehicleRepository.java
│   ├── ParkingSlotRepository.java
│   └── ParkingTicketRepository.java
├── entity/
│   ├── Vehicle.java
│   ├── ParkingSlot.java
│   └── ParkingTicket.java
├── dto/
│   ├── VehicleRequest.java
│   ├── ParkingSlotRequest.java
│   └── ParkRequest.java
└── exception/
    ├── GlobalExceptionHandler.java
    ├── ResourceNotFoundException.java
    └── BusinessException.java
```

---

## Business Rules

1. **Vehicle Registration:**
   - License plate must be unique
   - All fields are mandatory

2. **Parking Slot:**
   - Slot number must be unique
   - Slots are type-specific (CAR, BIKE, TRUCK)

3. **Parking Operations:**
   - Vehicle can only be parked if not already parked
   - Slot type must match vehicle type
   - System assigns first available slot of matching type
   - Vehicle cannot be unparked twice

4. **Data Validation:**
   - All required fields are validated
   - Invalid IDs return 404
   - Business rule violations return 400

---

## Future Enhancements

- [ ] Parking fee calculation based on duration
- [ ] Reservation system
- [ ] Multi-level parking support
- [ ] Payment integration
- [ ] Real-time slot availability dashboard
- [ ] Email/SMS notifications
- [ ] Vehicle exit time estimation

---

## License

MIT License