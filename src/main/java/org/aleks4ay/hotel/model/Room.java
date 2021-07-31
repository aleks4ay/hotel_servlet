package org.aleks4ay.hotel.model;

public class Room {
    private int number;
    private Enum<RoomStatus> roomStatus;
    private Enum<RoomCategory> roomCategory;
    private int guests;
    private String description;
    private double price;
    private byte[] roomView;

    public Room() {
    }

    public Room(int number, Enum<RoomCategory> roomCategory, int guests, String description, double price) {
        this.roomStatus = RoomStatus.EMPTY;
        this.number = number;
        this.roomCategory = roomCategory;
        this.guests = guests;
        this.description = description;
        this.price = price;
    }

    public Enum<RoomCategory> getRoomCategory() {
        return roomCategory;
    }

    public void setRoomCategory(Enum<RoomCategory> roomCategory) {
        this.roomCategory = roomCategory;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Enum<RoomStatus> getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(Enum<RoomStatus> roomStatus) {
        this.roomStatus = roomStatus;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public byte[] getRoomView() {
        return roomView;
    }

    public void setRoomView(byte[] roomView) {
        this.roomView = roomView;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomCategory=" + roomCategory +
                ", roomStatus=" + roomStatus +
                ", guests=" + guests +
                ", description='" + description + '\'' +
                ", number=" + number +
                ", price=" + price +
                '}';
    }
}
