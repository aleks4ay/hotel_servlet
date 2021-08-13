package org.aleks4ay.hotel.dao;

import org.aleks4ay.hotel.model.Order;
import org.aleks4ay.hotel.model.Room;
import org.aleks4ay.hotel.model.Schedule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.time.LocalDate;

import static org.junit.Assert.*;


public class OrderDaoTest {

    OrderDao dao;
    Connection connection;

    @Before
    public void setUp() throws Exception {
        connection = ConnectionPool.getConnection();
        dao = new OrderDao(connection);
    }

    @After
    public void tearDown() throws Exception {
        ConnectionPool.closeConnection(connection);
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