package org.aleks4ay.hotel.service;

import org.aleks4ay.hotel.dao.ConnectionPool;
import org.aleks4ay.hotel.dao.InvoiceDao;
import org.aleks4ay.hotel.dao.OrderDao;
import org.aleks4ay.hotel.dao.UserDao;
import org.aleks4ay.hotel.exception.CannotSaveException;
import org.aleks4ay.hotel.exception.NotEmptyRoomException;
import org.aleks4ay.hotel.exception.NotFoundException;
import org.aleks4ay.hotel.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class OrderService {
    private static final Logger log = LogManager.getLogger(OrderService.class);

    private final ConnectionPool connectionPool;
    private final UserService userService;
    private final RoomService roomService;
    private final InvoiceService invoiceService;

    public OrderService(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        this.userService = new UserService(connectionPool);
        this.roomService = new RoomService(connectionPool);
        this.invoiceService = new InvoiceService(connectionPool);
    }

    public Order findById(long id) {
        Connection conn = connectionPool.getConnection();
        Order order = new OrderDao(conn).findById(id).orElseThrow(
                () -> new NotFoundException("Order with id '" + id + "' not found"));
        if (order.getRoom() != null) {
            order.setRoom(roomService.findById(order.getRoom().getId()));
            Optional<Invoice> invoiceOptional = invoiceService.findByOrderId(order.getId());
            invoiceOptional.ifPresent(order::setInvoice);
        }
        connectionPool.closeConnection(conn);
        return order;
    }

    public List<Order> findAll(String sortMethod) {
        Connection conn = connectionPool.getConnection();
        List<Order> orders = new OrderDao(conn).findAll(sortMethod);
        Map<Long, User> users = userService.getAllAsMap("login");
        fillRoomAndInvoice(orders, users);
        connectionPool.closeConnection(conn);
        return orders;
    }

    public List<Order> findAllByUser(User user) {
        Connection conn = connectionPool.getConnection();
        List<Order> orders = new OrderDao(conn).findAllByUser(user.getId(), "id");

        Map<Long, User> users = Collections.singletonMap(user.getId(), user);
        fillRoomAndInvoice(orders, users);
        user.setOrders(orders);
        connectionPool.closeConnection(conn);
        return orders;
    }

    private void fillRoomAndInvoice(List<Order> orders, Map<Long, User> users) {
        Map<Long, Room> rooms = roomService.findAllAsMap("number");
        Map<Long, Invoice> invoicesByOrderId = invoiceService.findAllAsMap();
        for (Order o : orders) {
            o.setUser(users.get(o.getUser().getId()));
            if (o.getRoom() != null && rooms.containsKey(o.getRoom().getId())) {
                o.setRoom(rooms.get(o.getRoom().getId()));
                o.setInvoice(invoicesByOrderId.get(o.getId()));
            }
        }
    }

    public void updateStatus(Order order) {
        Connection conn = connectionPool.getConnection();
        new OrderDao(conn).updateStatus(order);
        connectionPool.closeConnection(conn);
    }


    public Order create(OrderDto orderDto, User user) {
        final LocalDate arrival = orderDto.getArrival();
        final LocalDate departure = orderDto.getDeparture();
        return buildOrder(orderDto.getNumber(), arrival, departure, user, Role.ROLE_USER);
    }

    private Order buildOrder(int roomNumber, LocalDate arrival, LocalDate departure, User user, Role role) {
        Room room = roomService.findByNumber(roomNumber);
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

    public Order updateOrderIfEmpty(Order order) {
        Connection conn = connectionPool.getConnection();
        OrderDao orderDao = new OrderDao(conn);
        long id = order.getRoom().getId();
        if (orderDao.checkRoomByRoomId(id, order.getArrival(), order.getDeparture()) > 0) {
            throw new NotEmptyRoomException("This room is occupied during this period!");
        }
        orderDao.updateRoom(order);
        orderDao.saveRoomLink(order);
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

    public void pay(Order order) throws SQLException {
        Connection conn = connectionPool.getConnection();
        try {
            conn.setAutoCommit(false);
            order.getUser().reduceBill(order.getCost());
            order.setStatus(Order.Status.PAID);
            order.getInvoice().setStatus(Invoice.Status.PAID);
            boolean result = new OrderDao(conn).updateStatus(order)
                    & new InvoiceDao(conn).updateStatus(order.getInvoice())
                    & new UserDao(conn).update(order.getUser());
            if (result) {
                conn.commit();
            } else {
                conn.rollback();
                order.setStatus(Order.Status.CONFIRMED);
                order.getInvoice().setStatus(Invoice.Status.NEW);
                order.getUser().addBill(order.getCost());
            }
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            log.warn("Cannot save pay. Order: {}. {}", order, e);
            throw new SQLException("Cannot save pay. Order: " + order, e);
        } finally {

            connectionPool.closeConnection(conn);
        }
    }

    public void setCancelInvoice() {
        Connection conn = connectionPool.getConnection();
        new OrderDao(conn).doInvoiceInspector();
        connectionPool.closeConnection(conn);
    }

    public List<Order> doPagination(int positionOnPage, int page, List<Order> entities) {
        return new UtilService<Order>().doPagination(positionOnPage, page, entities);
    }

    public OrderDto createOrderDto(HttpServletRequest request, Room room) {
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
