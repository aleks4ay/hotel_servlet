package org.aleks4ay.hotel.model;

import org.aleks4ay.hotel.service.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class User extends BaseEntity{

//    private long id;
    private String login;
    private String name;
    private String surname;
    private String password;
    private boolean active = true;
    private LocalDateTime registered = LocalDateTime.now();
    private Role role;
    private double bill;
//    private Set<Role> roles = new HashSet<>();

    private List<Order> orders = new ArrayList<>();

    public User() {
    }

    public static void main(String[] args) {
        User user1 = new User(12L, "login1", "name", "surname", "pass1");
        System.out.println("user1=" + user1);
        new UserService().create("login1", "name", "surname", "pass1");
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

/*    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }*/

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
//        return Encrypt.hash(password, "SHA-256");
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

    public double getBill() {
        return bill;
    }

    public void setBill(double bill) {
        this.bill = bill;
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

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
        order.setUser(this);
    }

    public boolean isAdmin() {
        return role.equals(Role.ROLE_ADMIN);
    }

    public boolean isManager() {
        return role.equals(Role.ROLE_MANAGER);
    }

    public boolean isClient() {
        return role.equals(Role.ROLE_USER);
    }

    public String getDateByPattern() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
        return registered.format(formatter);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", active=" + active +
                ", registered=" + registered +
                ", role=" + role +
                ", orders=" + orders +
                '}';
    }
}
