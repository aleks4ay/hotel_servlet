package org.aleks4ay.hotel.model;

public class BaseEntity {

    private long id;

    BaseEntity() {
    }

    BaseEntity(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
