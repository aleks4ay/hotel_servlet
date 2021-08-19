package org.aleks4ay.hotel.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Order extends BaseEntity {

    private long id;
    private LocalDate arrival;
    private LocalDate departure;
    private int period;
    private int guests;
    private Category category;
    private LocalDateTime registered = LocalDateTime.now();
    private long correctPrice;

    private Status status;
    private User user;
    private Room room;
    private Invoice invoice;

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

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
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
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDateTime getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDateTime registered) {
        this.registered = registered;
    }

    public long getCorrectPrice() {
        return correctPrice;
    }

    public void setCorrectPrice(long correctPrice) {
        this.correctPrice = correctPrice;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public void setRoomDeeply(Room room) {
        this.room = room;
        this.correctPrice = room.getPrice();
        room.addOrder(this);
    }


    public Order(LocalDate arrival, LocalDate departure, int guests, Category category,
                 LocalDateTime registered, long correctPrice) {
        this.arrival = arrival;
        this.departure = departure;
        this.period = (int) this.arrival.until(this.departure, ChronoUnit.DAYS);
        this.guests = guests;
        this.category = category;
        this.registered = registered;
        this.correctPrice = correctPrice;
    }


    public long getCost() {
        return getCorrectPrice() * getPeriod();
    }

    public String getRegisteredStr() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
        return registered.format(formatter);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", arrival=" + arrival +
                ", departure=" + departure +
                ", period=" + period +
                ", guests=" + guests +
                ", category=" + category +
                ", registered=" + registered +
                ", correctPrice=" + correctPrice +
                ", status=" + status +
                ", user=" + user.getLogin() +
                '}';
    }

    public enum Status {
        NEW,
        BOOKED,
        CONFIRMED,
        PAID,
        CANCEL
    }
}
