package org.aleks4ay.hotel.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Schedule extends BaseEntity {
    private LocalDate arrival;
    private LocalDate departure;
    private RoomStatus status;

    private Room room;

    public Schedule() {
    }

    public Schedule(long id) {
        super(id);
    }

    public Schedule(LocalDate arrival, LocalDate departure, RoomStatus status, Room room) {
        this.arrival = arrival;
        this.departure = departure;
        this.status = status;
        this.room = room;
    }



    public LocalDate getArrival() {
        return arrival;
    }

    public void setArrival(LocalDate arrival) {
        this.arrival = arrival;
    }

    public LocalDate getDeparture() {
        return departure;
    }

    public void setDeparture(LocalDate departure) {
        this.departure = departure;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
//        room.addSchedule(this);
    }

    public int getPeriod() {
        return (int) getArrival().until(getDeparture(), ChronoUnit.DAYS);
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + getId() +
                "arrival=" + arrival +
                ", departure=" + departure +
                ", status=" + status +
                ", room=" + room.getNumber() +
                '}';
    }

    public enum RoomStatus {
        EMPTY,
        RESERVED,
        BOOKED,
        OCCUPIED,
        DISABLED;
    }
}
