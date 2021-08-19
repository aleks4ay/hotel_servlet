package org.aleks4ay.hotel.service;

import org.aleks4ay.hotel.dao.ConnectionPool;
import org.aleks4ay.hotel.dao.RoomDao;
import org.aleks4ay.hotel.model.Room;

import java.sql.Connection;
import java.util.*;
import java.util.stream.Collectors;

public class RoomService {

    private ConnectionPool connectionPool;

    public RoomService(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public Optional<Room> getById(Long id) {
        Connection conn = connectionPool.getConnection();
        Optional<Room> room = new RoomDao(conn).findById(id);
        if (room.isPresent()) {
//            final List<Order> orders = new OrderDao(conn).getOrderByRoomId(id);
//            room.get().setOrders(orders);
        }
        connectionPool.closeConnection(conn);
        return room;
    }

    public List<Room> getAll() {
        Connection conn = connectionPool.getConnection();
        List<Room> rooms = new RoomDao(conn).findAll();
//        final Map<Long, List<Order>> orderMap = orderService.getAllAsMapByRoomId();
        for (Room room : rooms) {
//            List<Order> orders = orderMap.get(room.getId());
//            if (orders != null) {
//                room.setOrders(orders);
//            }
        }
        connectionPool.closeConnection(conn);
        return rooms;
    }

    Map<Long, Room> getAllAsMap() {
        return getAll().stream()
                .collect(Collectors.toMap(Room::getId, r -> r));
    }

    public Optional<Room> create(Room room) {
        Connection conn = connectionPool.getConnection();
        Optional<Room> roomOptional = new RoomDao(conn).create(room);
        connectionPool.closeConnection(conn);
        return roomOptional;
    }

    public List<Room> getAllWithFilters(List<String> filters) {
        Connection conn = connectionPool.getConnection();
        String filterAsString = UtilService.filterFromListToString(filters);
        List<Room> rooms = new RoomDao(conn).findAllWithFilter(filterAsString);
        connectionPool.closeConnection(conn);
        return rooms;
    }

    public List<Room> doPagination(int positionOnPage, int page, List<Room> entities) {
        return new UtilService<Room>().doPagination(positionOnPage, page, entities);
    }
}
