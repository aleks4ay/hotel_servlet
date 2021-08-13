package org.aleks4ay.hotel.service;

import org.aleks4ay.hotel.dao.ConnectionPool;
import org.aleks4ay.hotel.dao.OrderDao;
import org.aleks4ay.hotel.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class OrderService {
    private static final Logger log = LogManager.getLogger(OrderService.class);

    private UserService userService = new UserService();
    private RoomService roomService = new RoomService();
    private ScheduleService scheduleService = new ScheduleService();

    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        final Optional<User> user1 = orderService.userService.getByLogin("12");
        orderService.create(20L, LocalDate.of(2021, 8, 12), LocalDate.of(2021, 8, 16), user1.get());

    }

    public Optional<Order> getById(Long id) {
        Connection conn = ConnectionPool.getConnection();
        OrderDao orderDao = new OrderDao(conn);
        Optional<Order> optional = orderDao.findById(id);
        if (optional.isPresent()) {
            Order order = optional.get();
            order.setUser(userService.getById(order.getUser().getId()).orElse(null));
            order.setRoom(roomService.getById(order.getRoom().getId()).orElse(null));
        }
        ConnectionPool.closeConnection(conn);
        return optional;
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

/*    public boolean delete(Long id) {
        Connection conn = ConnectionPool.getConnection();
        OrderDao orderDao = new OrderDao(conn);
        boolean result = orderDao.delete(id);
        ConnectionPool.closeConnection(conn);
        return result;
    }*/

    public Optional<Order> create(long room_id, LocalDate dateStart, LocalDate dateEnd, User user) {
        Optional<Order> orderOptional = Optional.empty();

        Connection connection = ConnectionPool.getConnection();
        OrderDao orderDao = new OrderDao(connection);

        Order builtOrder = buildOrder(room_id, dateStart, dateEnd, user);

        try {
            connection.setAutoCommit(false);

            boolean result = scheduleService.checkRoom(builtOrder.getSchedule());

            if (result) {
                result = scheduleService.createSchedule(builtOrder.getSchedule());
            }

            if (result) {
                orderOptional = orderDao.create(builtOrder);
                result = orderOptional.isPresent();
            }

            if (result) {
                connection.commit();
                Order o = orderOptional.get();
                log.info("Was create new Order from {} to {} for {}. Room: {}."
                        ,o.getSchedule().getArrival(), o.getSchedule().getDeparture()
                        ,o.getUser().getName(), o.getRoom().getNumber());
            } else {
                connection.rollback();
            }
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderOptional;
    }

    public List<Order> doPagination(int positionOnPage, int page, List<Order> entities) {
        return new UtilService<Order>().doPagination(positionOnPage, page, entities);
    }

    public boolean updateStatus(Order.Status status, long id) {
        Connection conn = ConnectionPool.getConnection();
        OrderDao orderDao = new OrderDao(conn);
        boolean result = orderDao.updateStatus(status.toString(), id);
        ConnectionPool.closeConnection(conn);
        return result;
    }

    private Order buildOrder(long room_id, LocalDate dateStart, LocalDate dateEnd, User user) {

        Room room = roomService.getById(room_id).orElse(null);

        Order tempOrder = new Order(room, LocalDateTime.now());
        user.addOrder(tempOrder);
        Order.Status orderStatus = Order.Status.CONFIRMED;
        Schedule.RoomStatus roomStatus = Schedule.RoomStatus.BOOKED;
        if (user.isManager()) {
            orderStatus = Order.Status.NEW;
            roomStatus = Schedule.RoomStatus.RESERVED;
        }
        Schedule schedule = new Schedule(dateStart, dateEnd, roomStatus, room);

        tempOrder.setRoom(room);
        tempOrder.setSchedule(schedule);
        tempOrder.setUser(user);
        user.addOrder(tempOrder);
        tempOrder.setStatus(orderStatus);
        return tempOrder;
    }
}
