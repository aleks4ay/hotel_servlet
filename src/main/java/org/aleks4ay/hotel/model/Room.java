package org.aleks4ay.hotel.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Room extends BaseEntity {

    private int number;
    private Category category;
    private int guests;
    private String description;
    private double price;
    private byte[] roomView;
    private List<Schedule> schedules = new ArrayList<>();

    public Room() {
    }

    public Room(long id) {
        super(id);
    }

    public Room(int number, Category category, int guests, String description, double price) {
        this.number = number;
        this.category = category;
        this.guests = guests;
        this.description = description;
        this.price = price;
    }

    public boolean isEmpty(LocalDate start, LocalDate end) {
        for (Schedule t : schedules) {
            if (!start.isBefore(t.getArrival()) && !start.isAfter(t.getDeparture())
                    || !end.isBefore(t.getArrival()) && !end.isAfter(t.getDeparture()) ) {
                System.out.println("found occupied room: " + start + " - " + end);
                System.out.println("Schedule: " + t.getArrival() + " - " + t.getDeparture());
                return false;
            }
        }
        return true;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        for ( Schedule sch : schedules) {
            sch.setRoom(this);
            this.schedules.add(sch);
        }
    }

    public void addSchedule(Schedule schedule) {
        schedule.setRoom(this);
        this.schedules.add(schedule);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public byte[] getRoomView() {
        return roomView;
    }

    public void setRoomView(byte[] roomView) {
        this.roomView = roomView;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + getId() +
                "number=" + number +
                ", category=" + category +
                ", guests=" + guests +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", roomView=" + Arrays.toString(roomView) +
                ", schedules=" + schedules +
                '}';
    }

}
