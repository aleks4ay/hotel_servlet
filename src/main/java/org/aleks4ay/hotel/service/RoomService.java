package org.aleks4ay.hotel.service;

import org.aleks4ay.hotel.dao.ConnectionPool;
import org.aleks4ay.hotel.dao.RoomDao;
import org.aleks4ay.hotel.model.Room;

import java.sql.Connection;
import java.util.List;

public class RoomService {

    public static void main(String[] args) {
        int y=70;
        for (long i = 20; i<20+y; i++) {
            RoomService roomService = new RoomService();
            final Room room = roomService.getById(i);
            if (room != null) {
                System.out.println(room);
            }
        }
    }

    public Room getById(Long id) {
        Connection conn = ConnectionPool.getConnection();
        RoomDao roomDao = new RoomDao(conn);
        Room room = roomDao.getById(id);
        ConnectionPool.closeConnection(conn);
        return room;
    }

    public List<Room> getAll() {
        Connection conn = ConnectionPool.getConnection();
        RoomDao roomDao = new RoomDao(conn);
        List<Room> rooms = roomDao.findAll();
        ConnectionPool.closeConnection(conn);
        return rooms;
    }

    public List<Room> getAllWithFilters(List<String> filters) {
        Connection conn = ConnectionPool.getConnection();
        RoomDao roomDao = new RoomDao(conn);
        String filterAsString = filterFromListToString(filters);
        List<Room> rooms = roomDao.findAllWithFilter(filterAsString);
        ConnectionPool.closeConnection(conn);
        return rooms;
    }

    public boolean delete(Long id) {
        Connection conn = ConnectionPool.getConnection();
        RoomDao roomDao = new RoomDao(conn);
        boolean result = roomDao.delete(id);
        ConnectionPool.closeConnection(conn);
        return result;
    }

    public Room update(Room room) {
        Connection conn = ConnectionPool.getConnection();
        RoomDao roomDao = new RoomDao(conn);
        room = roomDao.update(room);
        ConnectionPool.closeConnection(conn);
        return room;
    }

    public Room create(Room room) {
        Connection conn = ConnectionPool.getConnection();
        RoomDao roomDao = new RoomDao(conn);
        room = roomDao.create(room);
        ConnectionPool.closeConnection(conn);
        return room;
    }

    public List<Room> getAll(int positionOnPage, int page) {
        Connection conn = ConnectionPool.getConnection();
        RoomDao roomDao = new RoomDao(conn);
        List<Room> rooms = roomDao.findAll(positionOnPage, page);
        ConnectionPool.closeConnection(conn);
        return rooms;
    }

    public String filterFromListToString(List<String> filters) {
        StringBuilder sb = new StringBuilder();
        for (String f: filters) {
            if (sb.length() != 0) {
                sb.append(" and ");
            }
            sb.append(f);
        }
        return sb.append(";").toString();
    }
}
