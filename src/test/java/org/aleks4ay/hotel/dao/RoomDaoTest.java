package org.aleks4ay.hotel.dao;

import org.aleks4ay.hotel.exception.NotEmptyRoomException;
import org.aleks4ay.hotel.model.Category;
import org.aleks4ay.hotel.model.Room;

import org.junit.*;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.*;


public class RoomDaoTest {
    private ConnectionPool connectionPool = new ConnectionPoolTest();
    private RoomDao dao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        conn = connectionPool.getConnection();
        dao = new RoomDao(conn);
        Statement statement = conn.createStatement();
        statement.execute("delete from room where true;");
        statement.execute("ALTER sequence id_seq restart with 1;");
        statement.execute("insert into room (number, category, guests, description, img_name, price) VALUES " +
                "(101, 'STANDARD',1,'description 1','101.jpg', 410.00), " +
                "(102, 'SUITE',4,'description 2','102.jpg', 420.00);");
    }

    @After
    public void tearDown() {
        connectionPool.closeConnection(conn);
    }


    @Test
    public void getById() {
        Room expected = new Room(101, Category.STANDARD, 1, "description 1", 410, "101.jpg");
        expected.setId(1L);
        assertEquals(dao.findById(1L).get(), expected);
    }


    @Test
    public void findAll()  {
        final List<Room> rooms = dao.findAll("id");
        assertEquals(2, rooms.size());
    }


    @Test
    public void update()  {
        Room roomFromDb = dao.findById(1L).orElseThrow(() -> new NotEmptyRoomException("room not found"));
        roomFromDb.setGuests(3);
        dao.update(roomFromDb);
        Room roomAfterUpdate = dao.findById(1L).orElseThrow(() -> new NotEmptyRoomException("room not found"));
        assertEquals(roomFromDb, roomAfterUpdate);
    }

    @Test
    public void create() {
        Room room = new Room(301, Category.SUITE,1,"description 1", 880, "101.jpg");
        assertEquals(0L, room.getId());
        Optional<Room> roomOptional = dao.create(room);
        assertNotEquals(0L, roomOptional.get().getId());
    }

}