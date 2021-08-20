package org.aleks4ay.hotel.service;

import org.aleks4ay.hotel.dao.ConnectionPool;
import org.aleks4ay.hotel.dao.ConnectionPoolTest;
import org.aleks4ay.hotel.exception.NotEmptyRoomException;
import org.aleks4ay.hotel.model.*;

import org.aleks4ay.hotel.model.Order;


import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

public class OrderServiceTest {
    private User userOne;
    private final ConnectionPool connectionPool = new ConnectionPoolTest();
    private final OrderService service = new OrderService(connectionPool);
    private Connection conn;

    @Before
    public void getUserOne() {
        userOne = new User(
                "login1", "name1", "surname1",
                "0B14D501A594442A01C6859541BCB3E8164D183D32937B851835442F69D5C94E", true,
                LocalDateTime.of(2021, 9, 10, 15, 54, 44), 10);
        userOne.setId(1L);
        userOne.setRole(Role.ROLE_USER);
    }

    @Before
    public void setUp() throws Exception {
        conn = connectionPool.getConnection();
        Statement statement = conn.createStatement();
        statement.execute("delete from usr where true;");
        statement.execute("delete from orders where true;");
        statement.execute("delete from room where true;");
        statement.execute("ALTER sequence id_seq restart with 1;");
        statement.execute("insert into usr (login, name, surname, password, registered, active, bill) VALUES " +
                "('login1', 'name1', 'surname1', '0B14D501A594442A01C6859541BCB3E8164D183D32937B851835442F69D5C94E', " +
                "'2021-09-10T15:54:44.0', true, 10), " +
                "('login2', 'name2', 'surname2', '6CF615D5BCAAC778352A8F1F3360D23F02F34EC182E259897FD6CE485D7870D4', " +
                "'2021-09-10T15:54:44.0', true, 20), " +
                "('login3', 'name3', 'surname3', '5906AC361A137E2D286465CD6588EBB5AC3F5AE955001100BC41577C3D751764', " +
                "'2021-09-10T15:54:44.0', true, 30);");
        statement.execute("insert into user_roles (user_id, role) VALUES " +
                "(1, 'ROLE_USER'), (2, 'ROLE_USER'), (3, 'ROLE_ADMIN');");
        statement.execute("insert into room (number, category, guests, description, img_name, price) VALUES " +
                "(101, 'STANDARD',1,'description 1','101.jpg', 410.00), " +
                "(102, 'SUITE',4,'description 2','102.jpg', 420.00);");
        statement.execute("insert into orders (arrival, departure, category, guests, registered, correct_price, " +
                "period, status, user_id) VALUES " +
                "('2021-09-01', '2021-09-02', 'STANDARD', 1, '2021-09-10T15:54:44.0', 410, 1, 'CONFIRMED', 1), " +
                "('2021-09-05', '2021-09-07', 'STANDARD', 1, '2021-09-10T15:54:44.0', 410, 2, 'CONFIRMED', 1), " +
                "('2021-09-01', '2021-09-05', 'SUITE', 4, '2021-09-10T15:54:44.0', 410, 4, 'CONFIRMED', 1);");
        statement.execute("insert into order_room (order_id, room_id) VALUES (6, 4), (7, 4);");
    }

    @After
    public void tearDown() {
        connectionPool.closeConnection(conn);
    }

    @Test
    public void findAll() {
        List<Order> orders = service.findAll("id");
        String expected = "[Order{id=6, arrival=2021-09-01, departure=2021-09-02, period=1, guests=1, category=STANDARD" +
                ", registered=2021-09-10T15:54:44, correctPrice=410, status=CONFIRMED, room=Room{id=4, number=101" +
                ", category=STANDARD, guests=1, description='description 1', price=410, imgName='101.jpg'}, user=login1}, " +
                "Order{id=7, arrival=2021-09-05, departure=2021-09-07, period=2, guests=1, category=STANDARD" +
                ", registered=2021-09-10T15:54:44, correctPrice=410, status=CONFIRMED, room=Room{id=4, number=101" +
                ", category=STANDARD, guests=1, description='description 1', price=410, imgName='101.jpg'}, user=login1}, " +
                "Order{id=8, arrival=2021-09-01, departure=2021-09-05, period=4, guests=4, category=SUITE" +
                ", registered=2021-09-10T15:54:44, correctPrice=410, status=CONFIRMED, room=null, user=login1}]";
        assertEquals(expected, orders.toString());
    }

    @Test
    public void findAllByUser() {
        User user = userOne;
        List<Order> orders = service.findAllByUser(user);
        assertEquals(3, orders.size());
    }

    @Test
    public void create() {
        Room room = new Room(101, Category.STANDARD,1,"description 1",410, "101.jpg");
        room.setId(4);
        Order expected = new Order(room, LocalDateTime.of(2021, 9, 10, 15, 54, 44));
        expected.setUser(userOne);
        expected.setPeriod(5);
        expected.setArrival(LocalDate.of(2021, 9, 3));
        expected.setDeparture(LocalDate.of(2021, 9, 8));
        expected.setGuests(1);
        expected.setCategory(Category.STANDARD);
        expected.setStatus(Order.Status.CONFIRMED);
        expected.setId(0L);

        OrderDto orderDto = new OrderDto(101, Category.STANDARD, 1, "description 1",
                LocalDate.of(2021, 9, 3), LocalDate.of(2021, 9, 8), 410, "101.jpg");
        Order actual = service.create(orderDto, userOne);
        actual.setRegistered(LocalDateTime.of(2021, 9, 10, 15, 54, 44));
        assertEquals(expected, actual);
    }

    @Test
    public void saveOrderIfEmpty() {
        Room room = new Room(101, Category.STANDARD,1,"description 1",410, "101.jpg");
        room.setId(4);
        Order order = new Order(room, LocalDateTime.of(2021, 9, 10, 15, 54, 44));
        order.setUser(userOne);
        order.setPeriod(1);
        order.setGuests(1);
        order.setCategory(Category.STANDARD);
        order.setStatus(Order.Status.CONFIRMED);
        order.setArrival(LocalDate.of(2021, 9, 3));
        order.setDeparture(LocalDate.of(2021, 9, 4));
        order = service.saveOrderIfEmpty(order);
        assertEquals(order.getId(), 9);
    }

    @Test(expected = NotEmptyRoomException.class)
    public void saveOrderIfNotEmpty() {
        Room room = new Room(101, Category.STANDARD,1,"description 1",410, "101.jpg");
        room.setId(4);
        Order order = new Order(room, LocalDateTime.of(2021, 9, 10, 15, 54, 44));
        order.setUser(userOne);
        order.setPeriod(1);
        order.setGuests(1);
        order.setCategory(Category.STANDARD);
        order.setStatus(Order.Status.CONFIRMED);
        order.setArrival(LocalDate.of(2021, 9, 3));
        order.setDeparture(LocalDate.of(2021, 9, 5));
        service.saveOrderIfEmpty(order);
    }
}