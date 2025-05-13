package org.example;

import java.util.Date;
import java.util.Map;

public class ReservationRequest {
    private Date start;
    private Date end;
    private Map<RoomType, Integer> roomsNeeded;

    public ReservationRequest(Date start, Date end, Map<RoomType, Integer> roomsNeeded) {
        this.start = start;
        this.end = end;
        this.roomsNeeded = roomsNeeded;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public Map<RoomType, Integer> getRoomsNeeded() {
        return roomsNeeded;
    }
}

