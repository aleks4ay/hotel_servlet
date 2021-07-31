package org.aleks4ay.hotel.model;

public enum RoomCategory {
    STANDARD("STANDARD"),
    SUPERIOR("SUPERIOR"),
    FAMILY_ROOM("FAMILY_ROOM"),
    SUITE("SUITE"),
    DELUXE("DELUXE");

    private String title;

    RoomCategory(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "RoomCategory['" +
                title + '\'' +
                ']';
    }
}
