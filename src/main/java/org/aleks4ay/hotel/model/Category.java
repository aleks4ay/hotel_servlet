package org.aleks4ay.hotel.model;

public enum Category {
    STANDARD("STANDARD"),
    SUPERIOR("SUPERIOR"),
    FAMILY_ROOM("FAMILY_ROOM"),
    SUITE("SUITE"),
    DELUXE("DELUXE");

    private String title;

    Category(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Category['" +
                title + '\'' +
                ']';
    }
}
