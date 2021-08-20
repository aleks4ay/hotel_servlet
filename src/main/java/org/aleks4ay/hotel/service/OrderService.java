package org.aleks4ay.hotel.service;

import org.aleks4ay.hotel.dao.ConnectionPool;
import org.aleks4ay.hotel.dao.OrderDao;
import org.aleks4ay.hotel.exception.CannotSaveException;
import org.aleks4ay.hotel.exception.NotEmptyRoomException;
import org.aleks4ay.hotel.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class OrderService {
    private static final Logger log = LogManager.getLogger(OrderService.class);

    private final ConnectionPool connectionPool;
    private final UserService userService;
    private final RoomService roomService;

    public OrderService(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        this.userService = new UserService(connectionPool);
        this.roomService = new RoomService(connectionPool);
    }

    public List<Order> findAll(String sortMethod) {
        Connection conn = connectionPool.getConnection();
        List<Order> orders = new OrderDao(conn).findAll(sortMethod);
        Map<Long, User> users = userService.getAllAsMap("login");
        Map<Long, Room> rooms = roomService.getAllAsMap("number");

        for (Order o : orders) {
            o.setUser(users.get(o.getUser().getId()));
            if (o.getRoom() != null && rooms.containsKey(o.getRoom().getId())) {
                o.setRoom(rooms.get(o.getRoom().getId()));
            }
        }
        connectionPool.closeConnection(conn);
        return orders;
    }

    public List<Order> findAllByUser(User user) {
        Connection conn = connectionPool.getConnection();
        List<Order> orders = new OrderDao(conn).findAllByUser(user.getId(), "id");
        Map<Long, Room> rooms = roomService.getAllAsMap("number");
        for (Order o : orders) {
            o.setUserDeeply(user);
            if (o.getRoom() != null && rooms.containsKey(o.getRoom().getId())) {
                o.setRoom(rooms.get(o.getRoom().getId()));
            }
        }
        connectionPool.closeConnection(conn);
        return orders;
    }


    public Order create(OrderDto orderDto, User user) {
        final LocalDate arrival = orderDto.getArrival();
        final LocalDate departure = orderDto.getDeparture();
        return buildOrder(orderDto.getNumber(), arrival, departure, user, Role.ROLE_USER);
    }

    private Order buildOrder(int roomNumber, LocalDate arrival, LocalDate departure, User user, Role role) {
        Room room = roomService.getByNumber(roomNumber);
        Order tempOrder = new Order(arrival, departure, room.getGuests(), room.getCategory(), LocalDateTime.now(),
                room.getPrice());
        tempOrder.setRoom(room);
        tempOrder.setUser(user);
        user.addOrder(tempOrder);
        tempOrder.setStatus(role == Role.ROLE_MANAGER ? Order.Status.BOOKED : Order.Status.CONFIRMED);
        return tempOrder;
    }

    public Order createProposal(HttpServletRequest request, User user) {
        ArrivalDepartureDto arrivalDeparture = parseArrivalAndDeparture(request);
        int guests = Integer.parseInt(request.getParameter("guests"));
        Category category = Category.valueOf(request.getParameter("category"));
        Order tempOrder = new Order(arrivalDeparture.getArrival(), arrivalDeparture.getDeparture(),
                guests, category, LocalDateTime.now(), 0L);
        tempOrder.setUser(user);
        user.addOrder(tempOrder);
        tempOrder.setStatus(Order.Status.NEW);
        return tempOrder;
    }

    public Order saveOrderIfEmpty(Order order) {
        Connection conn = connectionPool.getConnection();
        long id = order.getRoom().getId();
        if (new OrderDao(conn).checkRoomByRoomId(id, order.getArrival(), order.getDeparture()) > 0) {
            throw new NotEmptyRoomException("This room is occupied during this period!");
        }
        order = new OrderDao(conn).create(order)
                .orElseThrow(() -> new NotEmptyRoomException("Room with number '" + id + "' occupied"));
        if (order.getRoom() != null) {
            new OrderDao(conn).saveRoomLink(order);
        }
        connectionPool.closeConnection(conn);
        return order;
    }

    public Order save(Order order) {
        Connection conn = connectionPool.getConnection();
        order = new OrderDao(conn).create(order)
                .orElseThrow(() -> new CannotSaveException("Cannot save order"));
        connectionPool.closeConnection(conn);
        return order;
    }

    public List<Order> doPagination(int positionOnPage, int page, List<Order> entities) {
        return new UtilService<Order>().doPagination(positionOnPage, page, entities);
    }


    public OrderDto createOrderDto(HttpServletRequest request, Room room) {
        /*HttpSession session = request.getSession();
        LocalDate start = session.getAttribute("arrival") == null ? LocalDate.now()
                : (LocalDate) session.getAttribute("arrival");
        LocalDate end = session.getAttribute("departure") == null ? LocalDate.now()
                : (LocalDate) session.getAttribute("departure");
        end = end.isEqual(start) ? start.plusDays(1) : end;
        start = start.isAfter(end) ? end.minusDays(1) : start;
        request.setAttribute("arrival", start);
        request.setAttribute("departure", end);*/
        ArrivalDepartureDto arrivalDeparture = parseArrivalAndDeparture(request);
        return new OrderDto(room.getNumber(), room.getCategory(), room.getGuests(), room.getDescription(),
                arrivalDeparture.getArrival(), arrivalDeparture.getDeparture(), room.getPrice(), room.getImgName());
    }

    public ArrivalDepartureDto parseArrivalAndDeparture(HttpServletRequest request) {
        HttpSession session = request.getSession();
        LocalDate start = session.getAttribute("arrival") == null ? LocalDate.now()
                : (LocalDate) session.getAttribute("arrival");
        LocalDate end = session.getAttribute("departure") == null ? LocalDate.now()
                : (LocalDate) session.getAttribute("departure");
        end = end.isEqual(start) ? start.plusDays(1) : end;
        start = start.isAfter(end) ? end.minusDays(1) : start;
        request.setAttribute("arrival", start);
        request.setAttribute("departure", end);
        return new ArrivalDepartureDto(start, end);
    }
}
