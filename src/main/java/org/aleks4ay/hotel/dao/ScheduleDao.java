package org.aleks4ay.hotel.dao;

import org.aleks4ay.hotel.dao.mapper.ScheduleMapper;
import org.aleks4ay.hotel.model.Schedule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDao {
    private Connection connection = null;
    private ScheduleMapper scheduleMapper;

    private static final Logger log = LogManager.getLogger(ScheduleDao.class);
    private static final String SQL_CREATE = "INSERT INTO timetable (arrival, departure, status, room_id) VALUES (?, ?, ?, ?);";

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
            e.printStackTrace();
        }
        return result == 1;
    }

    public boolean updateStatus(Schedule schedule) {
        try (PreparedStatement prepStatement = connection.prepareStatement("UPDATE timetable SET status=? WHERE id=?;")) {
            prepStatement.setString(1, schedule.getStatus().toString());
            prepStatement.setLong(2, schedule.getId());
            return prepStatement.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


/*    public Schedule create(Schedule schedule) {
        try (PreparedStatement prepStatement = connection.prepareStatement(SQL_CREATE, new String[]{"id"})) {
            scheduleMapper.insertToResultSet(prepStatement, schedule);
            prepStatement.executeUpdate();

            ResultSet rs = prepStatement.getGeneratedKeys();
            if (rs.next()) {
                schedule.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedule;
    }*/

    public List<Schedule> getScheduleByRoomId(long roomId) {
        List<Schedule> vacancies = new ArrayList<>();
        try (PreparedStatement st = connection.prepareStatement("SELECT * FROM timetable WHERE room_id=?")){
            st.setLong(1, roomId);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                vacancies.add(scheduleMapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vacancies;
    }
}
