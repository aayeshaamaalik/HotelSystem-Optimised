

# Hotel Room Reservation System

This project implements a modular **Hotel Room Reservation System** in Java, enhanced with an **LRU cache** to optimize repeated availability checks. The system supports room inventory management, reservation requests based on room types and quantities, and exception handling when rooms are unavailable.

---

## Key Components and Interacting Objects

| Class / Component                      | Responsibility                                                         |
| -------------------------------------- | ---------------------------------------------------------------------- |
| `RoomType`                             | Enum for room categories (e.g., SINGLE, DOUBLE)                        |
| `Room`                                 | Represents a hotel room with availability and type                     |
| `ReservationRequest`                   | User input: date range and required room types/quantities              |
| `Reservation`                          | Output: confirmed booking with list of rooms and reservation dates     |
| `Request`                              | Simplified key (start/end date) used for caching availability lookups  |
| `LRUCache<K, V>`                       | Generic least-recently-used cache implementation using `LinkedHashMap` |
| `Hotel`                                | Main controller that processes reservations and caches search results  |
| `NotEnoughRoomForReservationException` | Thrown when requested room types cannot be fulfilled                   |

---

## Class Hierarchy and Structure

```
Hotel
 ├── List<Room>
 ├── LRUCache<Request, Map<RoomType, Set<Room>>>
 └── makeReservation(request)
       └── handleSearchRequest(request) → returns cached or computed availability

ReservationRequest
 ├── Date start, end
 └── Map<RoomType, Integer> roomsNeeded

Reservation
 ├── List<Room>
 ├── Date start, end

Room
 ├── RoomType type
 └── boolean available
```

---

## LRU Caching Optimization

The system avoids redundant room availability calculations by caching results for previously searched date ranges using an **LRU (Least Recently Used)** cache.

### Cache Details:

* Implemented with `LinkedHashMap`
* Capacity-limited (e.g., 100 recent requests)
* Lookup key: `Request` object (start and end date)

### Example:

```java
private LRUCache<Request, Map<RoomType, Set<Room>>> cache = new LRUCache<>(100);
```

This is used internally by:

```java
handleSearchRequest(ReservationRequest request)
```

Which is called by:

```java
makeReservation(ReservationRequest request)
```

---

## Supported Functionalities

1. **Room Inventory Management**
   Rooms of different types can be added to the hotel.

2. **Make Reservations**
   Accepts a `ReservationRequest`, checks availability (via cache), and books available rooms.

3. **LRU Caching of Availability**
   Avoids scanning all rooms for repeated date range queries.

4. **Custom Exception Handling**
   If not enough rooms of a required type are available, a `NotEnoughRoomForReservationException` is thrown.

5. **Rollback on Partial Allocation**
   If only part of a request can be fulfilled, no rooms are reserved.

---

## Getting Started

To set up and run the project locally:

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/hotel-reservation-system.git
   ```

2. Navigate into the project directory:

   ```bash
   cd hotel-reservation-system
   ```

3. Open in your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse)

4. Compile and run the system.
   Ensure your environment supports **Java 8+**

---

## File Implementation Order

**RoomType → Room → ReservationRequest → Reservation → NotEnoughRoomForReservationException → Request → LRUCache → Hotel**

