package org.example;

import java.util.Date;
import java.util.List;

public class Reservation {
    private List<Room> rooms;
    private Date start;
    private Date end;

    public Reservation(List<Room> rooms, Date start, Date end) {
        this.rooms = rooms;
        this.start = start;
        this.end = end;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "Reservation from " + start + " to " + end + " for " + rooms.size() + " rooms.";
    }
}

