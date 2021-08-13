package org.aleks4ay.hotel.service;

import org.aleks4ay.hotel.dao.ConnectionPool;
import org.aleks4ay.hotel.dao.ScheduleDao;
import org.aleks4ay.hotel.model.Room;
import org.aleks4ay.hotel.model.Schedule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.*;

public class ScheduleService {
    private static final Logger log = LogManager.getLogger(ScheduleService.class);
//    private RoomService roomService = new RoomService();

    public Optional<Schedule> getById(long id) {
        Connection conn = ConnectionPool.getConnection();
        ScheduleDao dao = new ScheduleDao(conn);
        Optional<Schedule> scheduleOptional = dao.getById(id);
//        if (scheduleOptional.isPresent()) {
//            long roomId = scheduleOptional.get().getRoom().getId();
//            Schedule schedule = scheduleOptional.get();
//            schedule.setRoom(roomService.getById(roomId).orElse(null));
//        }
        ConnectionPool.closeConnection(conn);
        return scheduleOptional;
    }

    public List<Schedule> getAll() {
        Connection conn = ConnectionPool.getConnection();
        ScheduleDao dao = new ScheduleDao(conn);
//        Map<Long, Room> rooms = roomService.getAllAsMap();
        List<Schedule> schedules = dao.getAll();
//        for (Schedule s : schedules) {
//            s.setRoom(rooms.get(s.getRoom().getId()));
//        }
        ConnectionPool.closeConnection(conn);
        return schedules;
    }

    public Map<Long, Schedule> getAllAsMap() {
        List<Schedule> schedules = getAll();
        Map<Long, Schedule> scheduleMap = new HashMap<>();
        for (Schedule o : schedules) {
            scheduleMap.put(o.getId(), o);
        }
        return scheduleMap;
    }



    public Map<Long, List<Schedule>> getAllAsMapByRoomId() {
        List<Schedule> schedules = getAll();
        Map<Long, List<Schedule>> scheduleMap = new HashMap<>();
        for (Schedule item : schedules) {
            long roomId = item.getRoom().getId();
            if (!scheduleMap.containsKey(roomId)) {
                List<Schedule> newList = new ArrayList<>();
                newList.add(item);
                scheduleMap.put(roomId, newList);
            } else {
                scheduleMap.get(roomId).add(item);
            }
        }
        return scheduleMap;
    }



    public boolean checkRoom(Schedule schedule) {
        Connection conn = ConnectionPool.getConnection();
        ScheduleDao dao = new ScheduleDao(conn);

        final boolean result = dao.checkRoom(schedule);
        if (!result) {
            log.info("Selected room #{} already occupied.", schedule.getRoom().getNumber());
        }
        ConnectionPool.closeConnection(conn);
        return result;
    }

    public boolean createSchedule(Schedule schedule) {
        Connection conn = ConnectionPool.getConnection();
        ScheduleDao dao = new ScheduleDao(conn);

        final boolean result = dao.createSchedule(schedule);
        if (!result) {
            log.info("Can't insert new Schedule to DB. {}", schedule);
        }
        ConnectionPool.closeConnection(conn);
        return result;
    }

    public boolean updateStatus(Schedule schedule) {
        Connection conn = ConnectionPool.getConnection();
        ScheduleDao dao = new ScheduleDao(conn);
        boolean result = dao.updateStatus(schedule);
        ConnectionPool.closeConnection(conn);
        return result;
    }

    public List<Schedule> getScheduleByRoomId(long roomId) {
        Connection conn = ConnectionPool.getConnection();
        ScheduleDao dao = new ScheduleDao(conn);
        List<Schedule> schedules = dao.getScheduleByRoomId(roomId);
        ConnectionPool.closeConnection(conn);
        return schedules;
    }

}
