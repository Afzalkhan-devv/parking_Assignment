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
```

## API Endpoints

### Vehicle Management

#### Register a Vehicle
```http
POST /api/vehicles
Content-Type: application/json

{
  "licensePlate": "HR51CB5999",
  "ownerName": "Afzal khan",
  "vehicleType": "CAR"
}
```

**Response (201 Created):**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "licensePlate": "HR51CB5999",
  "ownerName": "Afzal khan",
  "vehicleType": "CAR",
  "registeredAt": "2025-10-16T10:30:00"
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
    "licensePlate": "HR51CB5999",
    "ownerName": "Afzal khan",
    "vehicleType": "CAR"
  },
  "slot": {
    "id": "660e8400-e29b-41d4-a716-446655440000",
    "slotNumber": "A-101",
    "slotType": "CAR",
    "isAvailable": false
  },
  "entryTime": "2025-10-16T11:00:00",
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
  "entryTime": "2025-10-16T11:00:00",
  "exitTime": "2025-10-16T13:30:00"
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
  "timestamp": "2025-10-16T10:30:00",
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
  "timestamp": "2025-10-16T10:30:00",
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
# Park vehicles until all CAR slots are full
# Then try to park another CAR

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