package org.aleks4ay.hotel.dao;

import org.aleks4ay.hotel.dao.mapper.ScheduleMapper;
import org.aleks4ay.hotel.model.Schedule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ScheduleDao {
    private static final Logger log = LogManager.getLogger(ScheduleDao.class);

    private Connection connection = null;
    private ScheduleMapper scheduleMapper;

    public ScheduleDao(Connection connection) {
        this.connection = connection;
        this.scheduleMapper = new ScheduleMapper();
    }


    public boolean delete(Schedule schedule) {
        int result = 0;
        try (PreparedStatement prepStatement = connection.prepareStatement("DELETE FROM timetable WHERE id=?;") ) {
            prepStatement.setLong(1, schedule.getId());

            result = prepStatement.executeUpdate();
        } catch (SQLException e) {
            log.warn("Exception during delete schedule '{}'. {}", schedule, e);
        }
        return result == 1;
    }


    public boolean updateStatus(Schedule schedule) {
        try (PreparedStatement prepStatement = connection.prepareStatement("UPDATE timetable SET status=? WHERE id=?;")) {
            prepStatement.setString(1, schedule.getStatus().toString());
            prepStatement.setLong(2, schedule.getId());
            return prepStatement.executeUpdate() == 1;

        } catch (SQLException e) {
            log.warn("Exception during update Status for schedule '{}'. {}", schedule, e);
        }
        return false;
    }

    public boolean checkRoom(Schedule schedule) {
        try (PreparedStatement ps = connection.prepareStatement("SELECT count(*) FROM timetable WHERE room_id=? AND " +
                "(arrival BETWEEN ? AND ? OR departure BETWEEN ? AND ?);")) {
            ps.setLong(1, schedule.getRoom().getId());
            ps.setDate(2, Date.valueOf(schedule.getArrival()));
            ps.setDate(4, Date.valueOf(schedule.getArrival()));

            ps.setDate(3, Date.valueOf(schedule.getDeparture()));
            ps.setDate(5, Date.valueOf(schedule.getDeparture()));
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) == 0;
        } catch (SQLException e) {
            log.warn("Exception during check room schedule '{}'. {}", schedule, e);
        }
        return false;
    }

    public boolean createSchedule(Schedule schedule) {
        try (PreparedStatement ps2 = connection.prepareStatement(
                "INSERT INTO timetable (arrival, departure, status, room_id) VALUES (?, ?, ?, ?);"
                , new String[]{"id"}) ) {
            ps2.setDate(1, Date.valueOf(schedule.getArrival()));
            ps2.setDate(2, Date.valueOf(schedule.getDeparture()));
            ps2.setString(3, schedule.getStatus().toString());
            ps2.setLong(4, schedule.getRoom().getId());
            ps2.executeUpdate();
            ResultSet rs2 = ps2.getGeneratedKeys();
            if (rs2.next()) {
                long scheduleId = rs2.getLong(1);
                schedule.setId(scheduleId);
                return true;
            } else {
                log.info("Can't insert new Schedule to DB. {}", schedule);
            }
        } catch (SQLException e) {
            log.warn("Exception during create schedule '{}'. {}", schedule, e);
        }
        return false;
    }


    public List<Schedule> getScheduleByRoomId(long roomId) {
        List<Schedule> schedules = new ArrayList<>();
        try (PreparedStatement st = connection.prepareStatement("SELECT * FROM timetable WHERE room_id=?")){
            st.setLong(1, roomId);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                schedules.add(scheduleMapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            log.warn("Exception during getting schedule for Room with id '{}'. {}", roomId, e);
        }
        return schedules;
    }


    public Optional<Schedule> getById(long id) {
        Optional<Schedule> scheduleOptional = Optional.empty();
        try (PreparedStatement st = connection.prepareStatement("SELECT * FROM timetable WHERE id=?")){
            st.setLong(1, id);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                scheduleOptional = Optional.of(scheduleMapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            log.warn("Exception during getting schedule with id '{}'. {}", id, e);
        }
        return scheduleOptional;
    }

    public List<Schedule> getAll() {
        List<Schedule> schedules = new ArrayList<>();
        try (Statement st = connection.createStatement()){

            ResultSet rs = st.executeQuery("SELECT * FROM timetable;");

            while (rs.next()) {
                schedules.add(scheduleMapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            log.warn("Exception during getting all schedule. {}", e);
        }
        return schedules;
    }
}
