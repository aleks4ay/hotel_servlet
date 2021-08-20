package org.aleks4ay.hotel.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class OrderDto {

    private Integer number;
    private Category category;
    private int guests;
    private String description;
    private LocalDate arrival;
    private LocalDate departure;
//    private String arrivalString;
//    private String departureString;
    private long correctPrice;
    private String imgName;

    public OrderDto() {
    }

    public OrderDto(Integer number, Category category, int guests, String description, LocalDate arrival,
                    LocalDate departure, long correctPrice, String imgName) {
        this.number = number;
        this.category = category;
        this.guests = guests;
        this.description = description;
        this.arrival = arrival;
        this.departure = departure;
        this.correctPrice = correctPrice;
        this.imgName = imgName;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
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
/*
    public String getArrivalString() {
        return arrivalString;
    }

    public void setArrivalString(String arrivalString) {
        this.arrivalString = arrivalString;
    }

    public String getDepartureString() {
        return departureString;
    }

    public void setDepartureString(String departureString) {
        this.departureString = departureString;
    }*/

    public long getCorrectPrice() {
        return correctPrice;
    }

    public void setCorrectPrice(long correctPrice) {
        this.correctPrice = correctPrice;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    private int getPeriod() {
        return (int) getArrival().until(getDeparture(), ChronoUnit.DAYS);
    }

    public double getCost() {
        return getCorrectPrice() * getPeriod();
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "number=" + number +
                ", category=" + category +
                ", guests=" + guests +
                ", description='" + description + '\'' +
                ", arrival=" + arrival +
                ", departure=" + departure +
//                ", arrivalString='" + arrivalString + '\'' +
//                ", departureString='" + departureString + '\'' +
                ", correctPrice=" + correctPrice +
                ", imgName='" + imgName + '\'' +
                '}';
    }
}
