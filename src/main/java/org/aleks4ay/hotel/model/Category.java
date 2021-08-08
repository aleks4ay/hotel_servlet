package org.aleks4ay.hotel.model;

import java.util.Arrays;
import java.util.List;

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

    public List<Category> getCategoryValues() {
        return Arrays.asList(Category.values());
    }
}
