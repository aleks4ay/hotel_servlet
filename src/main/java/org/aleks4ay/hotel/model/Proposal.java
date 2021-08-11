package org.aleks4ay.hotel.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

public class Proposal extends BaseEntity {
    private LocalDateTime registered = LocalDateTime.now();
    private LocalDate arrival;
    private LocalDate departure;
    private int period;
    private int guests;
    private Category category;
    private Status status;

    private User user;

    public Proposal() {
    }

    public Proposal(LocalDate arrival, LocalDate departure, int guests, Category category, User user) {
        this.arrival = arrival;
        this.departure = departure;
        this.category = category;
        this.guests = guests;
        this.user = user;
        setPeriod();
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
                "id=" + getId() +
                ", registered=" + registered +
                ", arrival=" + arrival +
                ", departure=" + departure +
                ", period=" + period +
                ", category=" + category +
                ", status=" + status +
                ", user=" + user.getLogin() +
                '}';
    }

    public enum Status {
        NEW,
        MANAGED,
        CONFIRMED;

        public String getTitle() {
            return this.toString();
        }
    }

    public List<Status> getStatusesValue() {
        return Arrays.asList(Status.values());
    }
}
