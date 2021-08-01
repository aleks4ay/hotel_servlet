package org.aleks4ay.hotel.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by aser on 31.07.2021.
 */
public class Proposal {
    private Long id;
    private LocalDateTime registered = LocalDateTime.now();
    private LocalDate arrival;
    private LocalDate departure;
    private int period;
    private int guests;
    private Category category;
    private boolean newItem;

    private User user;

    public Proposal() {
    }

    public Proposal(LocalDate arrival, LocalDate departure, int guests, Category category, User user, boolean newItem) {
        this.newItem = newItem;
        this.arrival = arrival;
        this.departure = departure;
        this.category = category;
        this.guests = guests;
        this.user = user;
        setPeriod();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isNewItem() {
        return newItem;
    }

    public void setNewItem(boolean newItem) {
        this.newItem = newItem;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    @Override
    public String toString() {
        return "Proposal{" +
                "id=" + id +
                ", registered=" + registered +
                ", arrival=" + arrival +
                ", departure=" + departure +
                ", period=" + period +
                ", category=" + category +
                ", user=" + user.getLogin() +
                '}';
    }
}
