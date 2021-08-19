package org.aleks4ay.hotel.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Room extends BaseEntity {

    private long id;
    private int number;
    private Category category;
    private int guests;
    private String description;
    private long price;
    private String imgName;

    private List<Order> orders = new ArrayList<>();

    public Room() {
    }

    public Room(long id) {
        super(id);
    }

    public Room(int number, Category category, int guests, String description, long price, String imgName) {
        this.number = number;
        this.category = category;
        this.guests = guests;
        this.description = description;
        this.price = price;
        this.imgName = imgName;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
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

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    void addOrder(Order order) {
        orders.add(order);
    }

    public boolean isEmpty(LocalDate start, LocalDate end) {
/*        for (Schedule t : schedules) {
            if (!start.isBefore(t.getArrival()) && !start.isAfter(t.getDeparture())
                    || !end.isBefore(t.getArrival()) && !end.isAfter(t.getDeparture()) ) {
                System.out.println("found occupied room: " + start + " - " + end);
                System.out.println("Schedule: " + t.getArrival() + " - " + t.getDeparture());
                return false;
            }
        }*/
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return getId() == room.getId() &&
                getNumber() == room.getNumber() &&
                getGuests() == room.getGuests() &&
                getPrice() == room.getPrice() &&
                getCategory() == room.getCategory() &&
                Objects.equals(getDescription(), room.getDescription()) &&
                Objects.equals(getImgName(), room.getImgName()) &&
                Objects.equals(getOrders(), room.getOrders());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNumber(), getCategory(), getGuests(), getDescription(), getPrice(), getImgName(), getOrders());
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", number=" + number +
                ", category=" + category +
                ", guests=" + guests +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", imgName='" + imgName + '\'' +
                '}';
    }
}
