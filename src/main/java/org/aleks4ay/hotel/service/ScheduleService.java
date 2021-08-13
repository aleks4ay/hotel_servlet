package org.aleks4ay.hotel.service;

import org.aleks4ay.hotel.dao.ConnectionPool;
import org.aleks4ay.hotel.dao.ScheduleDao;
import org.aleks4ay.hotel.model.Schedule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;

public class ScheduleService {
    private static final Logger log = LogManager.getLogger(ScheduleService.class);

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
