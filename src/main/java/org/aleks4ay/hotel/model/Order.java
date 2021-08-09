package org.aleks4ay.hotel.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Order extends BaseEntity {

//    private long id;
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

    public Order(long id, Room room, LocalDateTime registered, LocalDate arrival, LocalDate departure) {
        this.orderStatus = OrderStatus.NEW;
        setId(id);//id = id;
        this.room = room;
        this.correctPrice = room.getPrice();
        this.registered = registered;
        this.arrival = arrival;
        this.departure = departure;
        setPeriod();
    }


/*    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }*/

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

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setPeriod() {
        this.period = (int) getArrival().until(getDeparture(), ChronoUnit.DAYS);
    }

    public double getCorrectPrice() {
        return correctPrice;
    }

    public void setCorrectPrice(Double price) {
        this.correctPrice = price;
    }

    public double getCost() {
        return getCorrectPrice() * getPeriod();
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + getId() +
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
