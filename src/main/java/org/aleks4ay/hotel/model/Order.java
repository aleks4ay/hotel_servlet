package org.aleks4ay.hotel.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Order {

    private Long id;
    private Room room;
    private LocalDateTime registered = LocalDateTime.now();
    private LocalDate arrival;
    private LocalDate departure;
    private int period;
    private double correctPrice;
//    private double cost;
    private OrderStatus orderStatus;

    private User user;

    public Order() {
    }

    public Order(Long id, Room room, LocalDateTime registered, LocalDate arrival, LocalDate departure) {
        this.orderStatus = OrderStatus.NEW;
        this.id = id;
        this.room = room;
        this.registered = registered;
        this.arrival = arrival;
        this.departure = departure;
        setPeriod();
        setCorrectPrice();
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    }

    public LocalDateTime getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDateTime registered) {
        this.registered = registered;
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

    public int getPeriod() {
        if (this.period == 0) {
            return (int) getArrival().until(getDeparture(), ChronoUnit.DAYS);
        }
        return period;
    }

    public void setPeriod() {
        this.period = (int) getArrival().until(getDeparture(), ChronoUnit.DAYS);
    }

    public double getCorrectPrice() {
        return correctPrice;
    }

    public void setCorrectPrice() {
        this.correctPrice = this.room.getPrice();
    }

    public double getCost() {
        return getCorrectPrice() * getPeriod();
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", room=" + room +
                ", orderStatus=" + orderStatus +
                ", registered=" + registered +
                ", arrival=" + arrival +
                ", departure=" + departure +
                ", correctPrice=" + correctPrice +
                ", cost=" + getCost() +
//                ", user=" + user +
                '}';
    }
}
