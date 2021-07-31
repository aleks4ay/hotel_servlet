package org.aleks4ay.hotel.run;

import org.aleks4ay.hotel.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by aser on 30.07.2021.
 */
public class RunDao {
//    private RunDao runDao;
    private List<User> users = new LinkedList<>();
    private List<Room> rooms = new LinkedList<>();
    private List<Order> orders = new LinkedList<>();

    public RunDao() {
//        this.runDao = new RunDao();
        init();
    }

    public static void main(String[] args) {
        RunDao runDao = new RunDao();
//        runDao.getOrders().forEach(System.out::println);
        runDao.getUsers().forEach(System.out::println);

    }

    public void init() {
        Room room1 = new Room(101, RoomCategory.STANDARD, 2, "Номер с видом на море", 1_600.0);
        Room room2 = new Room(106, RoomCategory.SUPERIOR, 3, "Номер с wi-fi и видом на море", 2_800.0);
        Room room3 = new Room(501, RoomCategory.DELUXE, 5, "Люкс с бассейном", 100_000.0);

        User user1 = new User("login1", "pass1");
        User user2 = new User("login2", "pass2");
        User manager = new User("login3", "pass3");
        manager.addRole(Role.ROLE_MANAGER);
        User admin = new User("adm", "adm");
        admin.addRole(Role.ROLE_ADMIN);
        user1.setName("Alex");
        user2.setName("Vlad");
        manager.setName("Dima");
        admin.setName("Nata");

        user1.setSurname("White");
        user2.setSurname("Yama");
        manager.setSurname("Voronin");
        admin.setSurname("Lev");


        Order order1 = new Order(2L, room1, LocalDateTime.now(), LocalDate.of(2021, 7, 30), LocalDate.of(2021, 8, 2));
        Order order2 = new Order(3L, room1, LocalDateTime.now(), LocalDate.of(2021, 8, 2), LocalDate.of(2021, 8, 5));
        Order order3 = new Order(4L, room2, LocalDateTime.now(), LocalDate.of(2021, 8, 4), LocalDate.of(2021, 8, 8));
        Order order4 = new Order(5L, room2, LocalDateTime.now(), LocalDate.of(2021, 8, 5), LocalDate.of(2021, 8, 6));
        Order order5 = new Order(6L, room3, LocalDateTime.now(), LocalDate.of(2021, 8, 8), LocalDate.of(2021, 8, 10));

        user1.addOrder(order1);
        user2.addOrder(order2);
        manager.addOrder(order3);
        user2.addOrder(order4);
        user1.addOrder(order5);
        users.addAll(Arrays.asList(user1, user2, manager, admin));
        rooms.addAll(Arrays.asList(room1, room2, room3));
        orders.addAll(Arrays.asList(order1, order2, order3, order4, order5));
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
