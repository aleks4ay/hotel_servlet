package org.aleks4ay.hotel.model;

import java.time.LocalDate;

public class ArrivalDepartureDto {

    private LocalDate arrival;
    private LocalDate departure;

    public ArrivalDepartureDto() {
    }

    public ArrivalDepartureDto(LocalDate arrival, LocalDate departure) {
        this.arrival = arrival;
        this.departure = departure;
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

    @Override
    public String toString() {
        return "Arrival-Departure: {" + arrival + " -> " + departure + "}";
    }
}
