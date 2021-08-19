package org.aleks4ay.hotel.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Invoice {

    private long id;
    private LocalDateTime registered = LocalDateTime.now();
    private long cost;
    private Invoice.Status status;
    private Order order;


    public Invoice() {
    }

    public Invoice(LocalDateTime registered, Status status, Order order) {
        this.registered = registered;
        this.cost = order.getCost();
        this.status = status;
        this.order = order;
        order.setInvoice(this);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDateTime registered) {
        this.registered = registered;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getRegisteredStr() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
        return registered.format(formatter);
    }
    public String getEndDateStr() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
        return registered.plusDays(2).format(formatter);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", registered=" + registered +
                ", cost=" + cost +
                ", status=" + status +
//                ", order=" + order +
                '}';
    }

    public enum Status {
        NEW,
        PAID,
        CANCEL
    }
}
