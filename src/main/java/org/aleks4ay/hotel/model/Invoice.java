package org.aleks4ay.hotel.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Invoice extends BaseEntity{

    private LocalDateTime registered = LocalDateTime.now();
    private long cost;
    private Invoice.Status status;
    private Order order;


    public Invoice() {
    }

    public Invoice(long id) {
        super(id);
    }

    public Invoice(LocalDateTime registered, Status status, Order order) {
        this.registered = registered;
        this.cost = order.getCost();
        this.status = status;
        this.order = order;
        order.setInvoice(this);
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
                "id=" + getId() +
                ", registered=" + registered +
                ", cost=" + cost +
                ", status=" + status +
                ", order=" + order.getId() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return getCost() == invoice.getCost() &&
                Objects.equals(getRegistered(), invoice.getRegistered()) &&
                getStatus() == invoice.getStatus() &&
                Objects.equals(getOrder(), invoice.getOrder());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRegistered(), getCost(), getStatus(), getOrder());
    }

    public enum Status {
        NEW,
        PAID,
        CANCEL
    }
}
