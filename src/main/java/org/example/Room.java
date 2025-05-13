package org.example;

public class Room {
    private RoomType type;
    private boolean available;

    public Room(RoomType type) {
        this.type = type;
        this.available = true;
    }

    public RoomType getType() {
        return type;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return type + " Room [" + (available ? "Available" : "Occupied") + "]";
    }
}
