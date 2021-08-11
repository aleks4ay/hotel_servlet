package org.aleks4ay.hotel.model;

import java.time.LocalDateTime;

public class Order extends BaseEntity {

    private LocalDateTime registered = LocalDateTime.now();
    private double correctPrice;
    private Status status;

    private Room room;
    private User user;
    private Schedule schedule;

    public Order() {
    }

    public Order(long id) {
        super(id);
    }

    public Order(Room room, LocalDateTime registered) {
        this.room = room;
        this.correctPrice = room.getPrice();
        this.registered = registered;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
        this.correctPrice = room.getPrice();
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public LocalDateTime getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDateTime registered) {
        this.registered = registered;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getCorrectPrice() {
        return correctPrice;
    }

    public void setCorrectPrice(Double price) {
        this.correctPrice = price;
    }

    public double getCost() {
        return getCorrectPrice() * getSchedule().getPeriod();
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + getId() +
                ", registered=" + registered +
                ", correctPrice=" + correctPrice +
                ", status=" + status +
                ", room=" + room.getNumber() +
                ", user=" + user.getLogin() +
                '}';
    }

    public enum Status {
        NEW,
        CONFIRMED,
//        MANAGED,
        PAID,
        CANCELED,
        COMPLETED
    }
}
