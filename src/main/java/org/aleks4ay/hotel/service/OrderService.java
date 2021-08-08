package org.aleks4ay.hotel.service;

import org.aleks4ay.hotel.dao.ConnectionPool;
import org.aleks4ay.hotel.dao.OrderDao;
import org.aleks4ay.hotel.model.*;

import java.sql.Connection;
import java.util.*;

public class OrderService {
    private UserService userService = new UserService();
    private RoomService roomService = new RoomService();

    public static void main(String[] args) {
        int y=1600;
        for (long i = 1000001; i<1000001+y; i++) {
            OrderService service = new OrderService();
            final Order order = service.getById(i);
            if (order != null) {
                System.out.print(order);
            }
        }
    }

    public Order getById(Long id) {
        Connection conn = ConnectionPool.getConnection();
        OrderDao orderDao = new OrderDao(conn);
        Order order = orderDao.getById(id);
        if (order != null) {
            order.setUser(userService.getById(order.getUser().getId()));
            order.setRoom(roomService.getById(order.getRoom().getId()));
        }
        ConnectionPool.closeConnection(conn);
        return order;
    }

    public List<Order> getAll() {
        Connection conn = ConnectionPool.getConnection();
        OrderDao orderDao = new OrderDao(conn);
        List<Order> orders = orderDao.findAll();
        Map<Long, Room> rooms = new HashMap<>();
        Map<Long, User> users = new HashMap<>();

        for (Room r : roomService.getAll()) {
            rooms.put(r.getId(), r);
        }
        for (User u : userService.getAll()) {
            users.put(u.getId(), u);
        }

        for (Order o : orders) {
            o.setUser(users.get(o.getUser().getId()));
            o.setRoom(rooms.get(o.getRoom().getId()));
        }
        ConnectionPool.closeConnection(conn);
        return orders;
    }

    public List<Order> getAllByUser(User user) {
        Connection conn = ConnectionPool.getConnection();
        OrderDao orderDao = new OrderDao(conn);
        List<Order> orders = new ArrayList<>();
        Map<Long, Room> rooms = new HashMap<>();

        for (Room r : roomService.getAll()) {
            rooms.put(r.getId(), r);
        }
        for (Order o : orderDao.findAll()) {
            if (o.getUser().getId() == user.getId()) {
                o.setUser(user);
                o.setRoom(rooms.get(o.getRoom().getId()));
                orders.add(o);
            }
        }
        ConnectionPool.closeConnection(conn);
        return orders;
    }

    public boolean delete(Long id) {
        Connection conn = ConnectionPool.getConnection();
        OrderDao orderDao = new OrderDao(conn);
        boolean result = orderDao.delete(id);
        ConnectionPool.closeConnection(conn);
        return result;
    }

    public Order create(Order order) {
        Connection conn = ConnectionPool.getConnection();
        OrderDao orderDao = new OrderDao(conn);
        order = orderDao.create(order);
        ConnectionPool.closeConnection(conn);
        return order;
    }

    public List<Order> getAll(int positionOnPage, int page) {
        Connection conn = ConnectionPool.getConnection();
        OrderDao orderDao = new OrderDao(conn);
        List<Order> orders = orderDao.findAll(positionOnPage, page);
        ConnectionPool.closeConnection(conn);
        return orders;
    }

    public boolean updateStatus(OrderStatus status, long id) {
        Connection conn = ConnectionPool.getConnection();
        OrderDao orderDao = new OrderDao(conn);
        boolean result = orderDao.updateStatus(status.toString(), id);
        ConnectionPool.closeConnection(conn);
        return result;
    }
}
