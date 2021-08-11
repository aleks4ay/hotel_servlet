package org.aleks4ay.hotel.service;

import org.aleks4ay.hotel.dao.ConnectionPool;
import org.aleks4ay.hotel.dao.RoomDao;
import org.aleks4ay.hotel.model.BaseEntity;
import org.aleks4ay.hotel.model.Room;

import java.sql.Connection;
import java.util.ArrayList;
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
/*
    public List<Room> getAll(int positionOnPage, int page*//*, List<String> filters*//*) {
        Connection conn = ConnectionPool.getConnection();
        RoomDao roomDao = new RoomDao(conn);
        List<Room> allRoom = roomDao.findAll();
        List<Room> roomsAfterFilter = getAllWithFilters(filters uu);
        List<Room> roomsAfterPagination = doPagination(positionOnPage, page, allRoom);
        ConnectionPool.closeConnection(conn);
        return roomsAfterPagination;
    }*/

    public List<Room> getAllWithFilters(List<String> filters) {
        Connection conn = ConnectionPool.getConnection();
        RoomDao roomDao = new RoomDao(conn);
        String filterAsString = UtilService.filterFromListToString(filters);
        List<Room> rooms = roomDao.findAllWithFilter(filterAsString);
        ConnectionPool.closeConnection(conn);
        return rooms;
    }

/*    public String filterFromListToString(List<String> filters) {
        StringBuilder sb = new StringBuilder();
        for (String f: filters) {
            if (sb.length() != 0) {
                sb.append(" and ");
            }
            sb.append(f);
        }
        return sb.append(";").toString();
    }*/

    public List<Room> doPagination(int positionOnPage, int page, List<Room> entities) {
        return new UtilService<Room>().doPagination(positionOnPage, page, entities);
/*        int startPosition = positionOnPage * (page - 1);
        List<Room> roomsAfterFilter = new ArrayList<>();

        if (entities.size() > startPosition) {
            for (int i = startPosition; i < startPosition + positionOnPage; i++) {
                if (i >= entities.size()) {
                    break;
                }
                roomsAfterFilter.add(entities.get(i));
            }
            return roomsAfterFilter;
        }
        return new ArrayList<>();*/
    }
}
