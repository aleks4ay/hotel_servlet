package org.aleks4ay.hotel.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Order {

    private Long id;
    private Room room;
    private LocalDateTime registered = LocalDateTime.now();
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private int period;
    private double correctPrice;
//    private double cost;
    private Enum<OrderStatus> orderStatus;

    private User user;

    public Order() {
    }

    public Order(Long id, Room room, LocalDateTime registered, LocalDate dateStart, LocalDate dateEnd) {
        this.orderStatus = OrderStatus.NEW;
        this.id = id;
        this.room = room;
        this.registered = registered;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
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

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod() {
        this.period = (int) getDateStart().until(getDateEnd(), ChronoUnit.DAYS);
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
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                ", correctPrice=" + correctPrice +
                ", cost=" + getCost() +
//                ", user=" + user +
                '}';
    }
}
