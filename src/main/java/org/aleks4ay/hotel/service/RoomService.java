package org.aleks4ay.hotel.service;

import org.aleks4ay.hotel.dao.ConnectionPool;
import org.aleks4ay.hotel.dao.RoomDao;
import org.aleks4ay.hotel.dao.ScheduleDao;
import org.aleks4ay.hotel.model.BaseEntity;
import org.aleks4ay.hotel.model.Room;
import org.aleks4ay.hotel.model.Schedule;

import java.sql.Connection;
import java.util.*;

public class RoomService {
    private ScheduleService scheduleService = new ScheduleService();

    public static void main(String[] args) {
        RoomService roomService = new RoomService();
        final List<Room> all = roomService.getAll();
        all.forEach(System.out::println);
    }

    public Optional<Room> getById(Long id) {
        Connection conn = ConnectionPool.getConnection();
        RoomDao roomDao = new RoomDao(conn);
        ScheduleDao scheduleDao = new ScheduleDao(conn);
        Optional<Room> room = roomDao.findById(id);
        if (room.isPresent()) {
            final List<Schedule> schedules = scheduleDao.getScheduleByRoomId(id);
            room.get().setSchedules(schedules);
        }
        ConnectionPool.closeConnection(conn);
        return room;
    }

    public List<Room> getAll() {
        Connection conn = ConnectionPool.getConnection();
        RoomDao roomDao = new RoomDao(conn);
        List<Room> rooms = roomDao.findAll();
        final Map<Long, List<Schedule>> scheduleMap = scheduleService.getAllAsMapByRoomId();
        System.out.println("scheduleMap.size() = " + scheduleMap.size());
        for (Room room : rooms) {
            List<Schedule> schedules = scheduleMap.get(room.getId());
//            System.out.println("room.getId()=" + room.getId());
            if (schedules != null) {
                room.setSchedules(schedules);
//                System.out.println("schedules.size() = " + schedules.size());
            } else {
//                System.out.println("room " + room.getId() + " is empty");
            }
        }
        ConnectionPool.closeConnection(conn);
        return rooms;
    }

    public Map<Long, Room> getAllAsMap() {
        List<Room> rooms = getAll();
        Map<Long, Room> roomMap = new HashMap<>();
        for (Room r : rooms) {
            roomMap.put(r.getId(), r);
        }
        return roomMap;
    }
/*
    public boolean delete(Long id) {
        Connection conn = ConnectionPool.getConnection();
        RoomDao roomDao = new RoomDao(conn);
        boolean result = roomDao.delete(id);
        ConnectionPool.closeConnection(conn);
        return result;
    }*/

/*    public Room update(Room room) {
        Connection conn = ConnectionPool.getConnection();
        RoomDao roomDao = new RoomDao(conn);
        room = roomDao.update(room);
        ConnectionPool.closeConnection(conn);
        return room;
    }*/

    public Optional<Room> create(Room room) {
        Connection conn = ConnectionPool.getConnection();
        RoomDao roomDao = new RoomDao(conn);
        Optional<Room> roomOptional = roomDao.create(room);
        ConnectionPool.closeConnection(conn);
        return roomOptional;
    }

    public List<Room> getAllWithFilters(List<String> filters) {
        Connection conn = ConnectionPool.getConnection();
        RoomDao roomDao = new RoomDao(conn);
        String filterAsString = UtilService.filterFromListToString(filters);
        List<Room> rooms = roomDao.findAllWithFilter(filterAsString);
        ConnectionPool.closeConnection(conn);
        return rooms;
    }

    public List<Room> doPagination(int positionOnPage, int page, List<Room> entities) {
        return new UtilService<Room>().doPagination(positionOnPage, page, entities);
    }
}
