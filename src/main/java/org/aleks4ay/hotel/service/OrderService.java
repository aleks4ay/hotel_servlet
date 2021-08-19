package org.aleks4ay.hotel.service;

import org.aleks4ay.hotel.dao.ConnectionPool;
import org.aleks4ay.hotel.dao.OrderDao;
import org.aleks4ay.hotel.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class OrderService {
    private static final Logger log = LogManager.getLogger(OrderService.class);

    private ConnectionPool connectionPool;
    private UserService userService;
    private RoomService roomService;

    public OrderService(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        this.userService = new UserService(connectionPool);
        this.roomService = new RoomService(connectionPool);
    }

    public List<Order> getAll() {
        Connection conn = connectionPool.getConnection();
        List<Order> orders = new OrderDao(conn).findAll();
        Map<Long, User> users = userService.getAllAsMap();
        Map<Long, Room> rooms = roomService.getAllAsMap();

        for (Order o : orders) {
            o.setUser(users.get(o.getUser().getId()));
            if (rooms.containsKey(o.getRoom().getId())) {
                o.setRoom(rooms.get(o.getRoom().getId()));
            }
        }
        connectionPool.closeConnection(conn);
        return orders;
    }

    public List<Order> getAllByUser(User user) {
        return getAll()
                .stream()
                .filter(order -> order.getUser().getId() == user.getId())
                .collect(Collectors.toList());
    }


    public Optional<Order> create(long room_id, LocalDate dateStart, LocalDate dateEnd, User user) {
        Optional<Order> orderOptional = Optional.empty();

        Connection conn = connectionPool.getConnection();

        Order builtOrder = buildOrder(room_id, dateStart, dateEnd, user);

        /*try {
            if (connection != null) {
                connection.setAutoCommit(false);
            }
            boolean result = checkRoom(builtOrder.getRoom());

            if (scheduleService.createSchedule(builtOrder.getSchedule())) {
                orderOptional = new OrderDao(connection).create(builtOrder);
                result = orderOptional.isPresent();
            }
            if (result && connection != null) {
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
        }*/
        return orderOptional;
    }

    private Order buildOrder(long room_id, LocalDate dateStart, LocalDate dateEnd, User user) {

        Room room = roomService.getById(room_id).orElse(null);

        Order tempOrder = new Order(room, LocalDateTime.now());
        user.addOrder(tempOrder);
        Order.Status orderStatus = Order.Status.CONFIRMED;
        /*Schedule.RoomStatus roomStatus = Schedule.RoomStatus.BOOKED;
        if (user.isManager()) {
            orderStatus = Order.Status.NEW;
            roomStatus = Schedule.RoomStatus.RESERVED;
        }*/

        tempOrder.setUser(user);
        user.addOrder(tempOrder);
        tempOrder.setStatus(orderStatus);
        return tempOrder;
    }
}
