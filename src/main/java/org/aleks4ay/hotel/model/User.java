package org.aleks4ay.hotel.model;

import org.aleks4ay.hotel.exception.NoMoneyException;
import org.aleks4ay.hotel.exception.NotFoundException;
import org.aleks4ay.hotel.service.UserService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class User extends BaseEntity{

    private String login;
    private String name;
    private String surname;
    private String password;
    private boolean active = true;
    private LocalDateTime registered = LocalDateTime.now();
    private BigDecimal bill;
    private Role role;

    private List<Order> orders = new ArrayList<>();

    public User() {
    }

    public User(long id) {
        super(id);
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(Long id, String login, String name, String surname, String password) {
        setId(id);
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.password = password;
    }

    public User(String login, String name, String surname, String password, boolean active, LocalDateTime registered,
                double money) {
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.active = active;
        this.registered = registered;
        setBill(money);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDateTime registered) {
        this.registered = registered;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public BigDecimal getBill() {
        return bill;
    }

    public void setBill(BigDecimal bigDecimal) {
        bill = bigDecimal;
        bill = bill.setScale(2, BigDecimal.ROUND_FLOOR);
    }

    public void setBill(double money) {
        bill = new BigDecimal(Double.toString(money));
        bill = bill.setScale(2, BigDecimal.ROUND_FLOOR);
    }

    public void addBill(double money) {
        if (bill == null) {
            bill = new BigDecimal(Double.toString(money));
            bill = bill.setScale(2, BigDecimal.ROUND_FLOOR);
        } else {
            bill = bill.add(new BigDecimal(Double.toString(money)));
        }
    }

    public void reduceBill(double money) {
        if (bill.doubleValue() > money) {
            BigDecimal reducedMoney = new BigDecimal(Double.toString(money));
            bill = bill.subtract(reducedMoney);
        } else {
            throw new NoMoneyException("Sorry, there are not enough funds in your account");
        }
    }

    public Order getOrderById(long id) {
        if (orders.isEmpty()) {
            throw new NotFoundException("Order with id = " + id + " not found");
        }
        for (Order o : this.orders) {
            if (o.getId() == id) {
                return o;
            }
        }
        throw new NotFoundException("Order with id = " + id + " not found");
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
        order.setUser(this);
    }

    public boolean isAdmin() {
        return role == Role.ROLE_ADMIN;
    }

    public boolean isManager() {
        return role == Role.ROLE_MANAGER;
    }

    public boolean isClient() {
        return role == Role.ROLE_USER;
    }

    public String getDateByPattern() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
        return registered.format(formatter);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", login='" + login +
                ", name='" + name +
                ", surname='" + surname +
                ", active=" + active +
                ", registered=" + registered +
                ", bill=" + bill +
                ", role=" + role +
                ", orders=" + orders +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isActive() == user.isActive() &&
                getLogin().equals(user.getLogin()) &&
                Objects.equals(getName(), user.getName()) &&
                Objects.equals(getSurname(), user.getSurname()) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                Objects.equals(getRegistered(), user.getRegistered()) &&
                Objects.equals(getBill(), user.getBill()) &&
                getRole() == user.getRole() &&
                Objects.equals(getOrders(), user.getOrders());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin(), getName(), getSurname(), getPassword(), isActive(), getRegistered(), getBill(),
                getRole(), getOrders());
    }

    //    public User getUser() {
//        return this;
//    }
}
