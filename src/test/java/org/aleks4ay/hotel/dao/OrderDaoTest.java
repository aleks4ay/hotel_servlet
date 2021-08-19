package org.aleks4ay.hotel.dao;

//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;

import org.junit.*;

import java.sql.Connection;


public class OrderDaoTest {

    private ConnectionPool connectionPool = new ConnectionPoolTest();
    private OrderDao dao;
    private Connection connection;

    @Before
    public void setUp() throws Exception {
        connection = connectionPool.getConnection();
        dao = new OrderDao(connection);
    }

    @After
    public void tearDown() throws Exception {
        connectionPool.closeConnection(connection);
    }

    @Test
    public void getById() throws Exception {
/*        String expected = "Order{id=1000003, registered=2021-08-08T00:02:17.449, correctPrice=0.0, status=CONFIRMED, room=0, user=null}";
        Order actual = dao.getById(1000003L);
        assertEquals(expected, actual.toString());*/
    }

    @Test
    public void findAll() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void create() throws Exception {
/*        Room room = new RoomDao(connection).getById(19L);
        Order tempOrder = dao.getById(1000003L);
        tempOrder.setRoom(room);
        Schedule schedule = new Schedule(LocalDate.of(2021, 8, 7), LocalDate.of(2021, 8, 10), Schedule.RoomStatus.BOOKED, room);
        tempOrder.setSchedule(schedule);
        Order actual = dao.create(tempOrder);
        assertNotNull(actual);*/
    }

    @Test
    public void updateStatus() throws Exception {

    }

}