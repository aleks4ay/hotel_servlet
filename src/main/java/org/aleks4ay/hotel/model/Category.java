package org.aleks4ay.hotel.model;

import java.util.Arrays;
import java.util.List;

public enum Category {
    STANDARD,
    SUPERIOR,
    FAMILY_ROOM,
    SUITE,
    DELUXE;

    public String getTitle() {
        return this.toString();
    }

    public List<Category> getCategoryValues() {
        return Arrays.asList(Category.values());
    }
}
