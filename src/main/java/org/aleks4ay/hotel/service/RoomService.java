package org.aleks4ay.hotel.service;

import org.aleks4ay.hotel.dao.ConnectionPool;
import org.aleks4ay.hotel.dao.RoomDao;
import org.aleks4ay.hotel.exception.AlreadyException;
import org.aleks4ay.hotel.exception.NotFoundException;
import org.aleks4ay.hotel.model.Room;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class RoomService {

    private final ConnectionPool connectionPool;

    public RoomService(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public Room findById(Long id) {
        Connection conn = connectionPool.getConnection();
        Room room = new RoomDao(conn).findById(id).orElseThrow(() -> new NotFoundException("Room with id=" + id + "not found"));
        connectionPool.closeConnection(conn);
        return room;
    }

    public Room findByNumber(int number) {
        Connection conn = connectionPool.getConnection();
        Room room = new RoomDao(conn).findByNumber(number)
                .orElseThrow(() -> new NotFoundException("Room with number=" + number + "not found"));
        connectionPool.closeConnection(conn);
        return room;
    }

    public List<Room> findAll(String sortMethod, List<String> filters) {
        Connection conn = connectionPool.getConnection();
        List<Room> rooms = new RoomDao(conn).findAllWithFilter(sortMethod, filters);
        connectionPool.closeConnection(conn);
        return rooms;
    }

    Map<Long, Room> findAllAsMap(String sortMethod) {
        return findAll(sortMethod, new ArrayList<>()).stream()
                .collect(Collectors.toMap(Room::getId, r -> r));
    }

    public Room create(Room room) {
        Connection conn = connectionPool.getConnection();
        Room result = new RoomDao(conn).create(room)
                .orElseThrow(() -> new AlreadyException("Room with number #" + room.getNumber() + "already exists"));
        connectionPool.closeConnection(conn);
        return result;
    }

    public boolean update(Room room) {
        Connection conn = connectionPool.getConnection();
        boolean result = new RoomDao(conn).update(room);
        connectionPool.closeConnection(conn);
        return result;
    }

    public List<Room> getRoomsWithFilter(HttpServletRequest request) {
        Connection conn = connectionPool.getConnection();
        RoomDao dao = new RoomDao(conn);
        List<String> filterList = new ArrayList<>();
        Object categoryObj = request.getSession().getAttribute("category");
        Object guestsObj = request.getSession().getAttribute("guests");
        Object arrivalObj = request.getSession().getAttribute("arrival");
        Object departureObj = request.getSession().getAttribute("departure");
        String sortMethod = (String) request.getAttribute("sortMethod");
        if (categoryObj != null) {
            filterList.add("category = '" + categoryObj + "'");
        }
        if (guestsObj != null) {
            filterList.add("guests = " + guestsObj);
        }
        List<Room> rooms;
        if (arrivalObj == null && departureObj == null) {
            rooms = dao.findAllWithFilter(sortMethod, filterList);
        } else if (departureObj == null) {
            rooms = dao.findEmptyRoom((LocalDate)arrivalObj, sortMethod, filterList);
        } else if (arrivalObj == null) {
            rooms = dao.findEmptyRoom((LocalDate)departureObj, sortMethod, filterList);
        } else {
            rooms = dao.findEmptyRoom((LocalDate) arrivalObj, (LocalDate) departureObj, sortMethod, filterList);
        }
        return rooms;
    }

    public List<Room> doPagination(int positionOnPage, int page, List<Room> entities) {
        return new UtilService<Room>().doPagination(positionOnPage, page, entities);
    }

    public void addOldValues(HttpServletRequest request, Room room) {
        request.setAttribute("roomExistMessage", "Room with this number exists!");
        request.setAttribute("oldNumber", room.getNumber());
        request.setAttribute("oldDescription", room.getDescription());
        request.setAttribute("oldGuests", room.getGuests());
        request.setAttribute("oldPrice", room.getPrice());
        request.setAttribute("oldCategory", room.getCategory());
    }
}
