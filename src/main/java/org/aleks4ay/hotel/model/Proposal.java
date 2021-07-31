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
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private int period;
    private int guests;
    private RoomCategory category;

    private User user;

    public Proposal() {
    }

    public Proposal(LocalDate dateStart, LocalDate dateEnd, int guests, RoomCategory category, User user) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
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

    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }

    public RoomCategory getCategory() {
        return category;
    }

    public void setCategory(RoomCategory category) {
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
            return (int) getDateStart().until(getDateEnd(), ChronoUnit.DAYS);
        }
        return period;
    }

    public void setPeriod() {
        this.period = (int) getDateStart().until(getDateEnd(), ChronoUnit.DAYS);
    }

    @Override
    public String toString() {
        return "Proposal{" +
                "id=" + id +
                ", registered=" + registered +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                ", period=" + period +
                ", category=" + category +
                ", user=" + user.getLogin() +
                '}';
    }
}
