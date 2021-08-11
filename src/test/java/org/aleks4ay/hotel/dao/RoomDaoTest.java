package org.aleks4ay.hotel.dao;

import org.aleks4ay.hotel.model.Room;
import org.aleks4ay.hotel.service.RoomService;
import org.aleks4ay.hotel.service.UtilService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class RoomDaoTest {
    private Room room1, room2, room3;
    private List<Room> rooms = new LinkedList<>();
    private RoomDao roomDao;
    private Connection conn;

    @Before
    public void setUp() {
        this.conn = ConnectionPool.getConnection();
        this.roomDao = new RoomDao(conn);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getById() throws Exception {

    }

    @Test
    public void findAll() throws Exception {

    }

    @Test
    public void findAllWithFilter() throws Exception {
        List<String> filters = new ArrayList<>();
        filters.add(" guests = " + "3");
        filters.add(" category = " + "'STANDARD'");
        String filterAsString = UtilService.filterFromListToString(filters);
        List<Room> rooms = roomDao.findAllWithFilter(filterAsString);
//        List<Room> roomsAfterFilter = new RoomService().getAllWithFilters(filters);
        rooms.forEach(System.out::println);
        assertEquals(1, rooms.size());
    }

    @Test
    public void findAll1() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void create() throws Exception {
        ConnectionPool.closeConnection(this.conn);
    }

}