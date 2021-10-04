package org.aleks4ay.hotel.dao;

import org.aleks4ay.hotel.exception.NotEmptyRoomException;
import org.aleks4ay.hotel.model.*;
import org.aleks4ay.hotel.model.Order;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;


public class OrderDaoTest {
    private final ConnectionPool connectionPool = new ConnectionPoolTest();
    private OrderDao dao;
    private Connection conn;
    private Order orderOne;

    @Before
    public void setUp() throws Exception {
        conn = connectionPool.getConnection();
        dao = new OrderDao(conn);
        Statement statement = conn.createStatement();
        statement.execute("delete from usr where true;");
        statement.execute("delete from orders where true;");
        statement.execute("delete from room where true;");
        statement.execute("delete from invoice where true;");
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
                "('2021-09-01', '2021-09-05', 'SUITE', 4, '2021-09-10T15:54:44.0', 410, 4, 'NEW', 2);");
        statement.execute("insert into order_room (order_id, room_id) VALUES (6, 4), (7, 4);");
        statement.execute("insert into invoice (cost, registered, status) VALUES " +
                "(410, '2021-09-10T15:54:44.0', 'NEW'), (820, '2021-09-10T15:54:44.0', 'NEW');");
        statement.execute("insert into order_invoice (order_id, invoice_id) VALUES (6, 9), (7, 10);");
    }

    @Before
    public void setStartValue() {
        User userOne = new User(
                "login1", "name1", "surname1",
                "0B14D501A594442A01C6859541BCB3E8164D183D32937B851835442F69D5C94E", true,
                LocalDateTime.of(2021, 9, 10, 15, 54, 44), 10);
        userOne.setId(1L);
        userOne.setRole(Role.ROLE_USER);
        orderOne = new Order(LocalDate.of(2021, 9, 3), LocalDate.of(2021, 9, 4), 1, Category.STANDARD,
                LocalDateTime.of(2021, 9, 10, 15, 54, 44), 410);
        orderOne.setStatus(Order.Status.BOOKED);
        orderOne.setUser(userOne);
    }

    @After
    public void tearDown() {
        connectionPool.closeConnection(conn);
    }

    @Test
    public void findById() {
        System.out.println(dao.findById(6L).get());
        System.out.println(dao.findById(8L).get());
        assertEquals(Order.class, dao.findById(8L).get().getClass());
    }

    @Test
    public void findByIdIfNotRoom() {
        Order actual = dao.findById(8L).get();
        assertNull(actual.getRoom());
    }

    @Test
    public void findAll() {
        assertEquals(3, dao.findAll("id").size());
    }

    @Test
    public void findAllByUser() {
        List<Order> orders = dao.findAllByUser(1L, "id");
        assertEquals(2, orders.size());
    }

    @Test
    public void checkRoomByRoomIdIfEmpty() {
        int quantityOccupiedRoom = dao.checkRoomByRoomId(4L, LocalDate.of(2021, 9, 3), LocalDate.of(2021, 9, 4));
        assertEquals(0, quantityOccupiedRoom);
    }

    @Test
    public void checkRoomByRoomIdIfNotEmpty() {
        int quantityOccupiedRoom = dao.checkRoomByRoomId(4L, LocalDate.of(2021, 9, 2), LocalDate.of(2021, 9, 14));
        assertEquals(2, quantityOccupiedRoom);
    }

    @Test
    public void saveRoomLink() {
        Order expected = dao.create(orderOne).orElseThrow(() -> new NotEmptyRoomException("this room occupied"));
        Room room = new Room(4L);
        expected.setRoom(room);
        dao.saveRoomLink(expected);
        Order actual = dao.findById(11L).get();
        assertEquals(expected.getRoom(), actual.getRoom());
    }
}