package org.example;
import java.util.*;

public class Hotel {
    private List<Room> rooms;
    private LRUCache<Request, Map<RoomType, Set<Room>>> cache = new LRUCache<>(100);

    public Hotel() {
        this.rooms = new ArrayList<>();
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public Map<RoomType, Set<Room>> handleSearchRequest(ReservationRequest request) {
        Request key = new Request(request.getStart(), request.getEnd());

        // Check if result already exists in cache
        if (cache.containsKey(key)) {
            System.out.println("Cache hit for: " + key.getStart() + " to " + key.getEnd());
            return cache.get(key);
        }

        // If not cached, compute available rooms
        Map<RoomType, Set<Room>> available = new HashMap<>();
        for (Room room : rooms) {
            if (room.isAvailable()) {
                available.computeIfAbsent(room.getType(), k -> new HashSet<>()).add(room);
            }
        }

        // Save result to cache
        cache.put(key, available);
        return available;
    }

    public Reservation makeReservation(ReservationRequest request) throws NotEnoughRoomForReservationException {
        Map<RoomType, Set<Room>> availableRooms = handleSearchRequest(request);
        Map<RoomType, Integer> requestedRooms = request.getRoomsNeeded();
        List<Room> reservedRooms = new ArrayList<>();

        // Try to fulfill the room request
        for (Map.Entry<RoomType, Integer> entry : requestedRooms.entrySet()) {
            RoomType type = entry.getKey();
            int needed = entry.getValue();
            Set<Room> availableOfType = availableRooms.getOrDefault(type, Collections.emptySet());

            if (availableOfType.size() < needed) {
                throw new NotEnoughRoomForReservationException("Not enough rooms of type: " + type);
            }

            Iterator<Room> iterator = availableOfType.iterator();
            for (int i = 0; i < needed; i++) {
                Room room = iterator.next();
                room.setAvailable(false); // mark room as reserved
                reservedRooms.add(room);
                iterator.remove(); // remove from availability list
            }
        }

        return new Reservation(reservedRooms, request.getStart(), request.getEnd());
    }


    public Reservation createReservation(ReservationRequest request) throws NotEnoughRoomForReservationException {
        List<Room> reservedRooms = new ArrayList<>();
        Map<RoomType, Integer> roomsNeeded = request.getRoomsNeeded();
        Map<RoomType, Integer> roomsAllocated = new HashMap<>();

        // Initialize allocated count
        for (RoomType type : roomsNeeded.keySet()) {
            roomsAllocated.put(type, 0);
        }

        for (Room room : rooms) {
            if (!room.isAvailable()) continue;

            RoomType type = room.getType();
            if (roomsNeeded.containsKey(type) &&
                    roomsAllocated.get(type) < roomsNeeded.get(type)) {

                room.setAvailable(false);
                reservedRooms.add(room);
                roomsAllocated.put(type, roomsAllocated.get(type) + 1);
            }
        }

        // Verify if all required rooms were allocated
        for (RoomType type : roomsNeeded.keySet()) {
            if (!roomsAllocated.get(type).equals(roomsNeeded.get(type))) {
                // Roll back allocation
                for (Room r : reservedRooms) r.setAvailable(true);
                throw new NotEnoughRoomForReservationException("Not enough " + type + " rooms available.");
            }
        }

        return new Reservation(reservedRooms, request.getStart(), request.getEnd());
    }
}

