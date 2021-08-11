package org.aleks4ay.hotel.dao.mapper;

import org.aleks4ay.hotel.model.*;

import java.sql.*;

public class ScheduleMapper implements ObjectMapper<Schedule> {
    @Override
    public Schedule extractFromResultSet(ResultSet rs) throws SQLException {
        Schedule schedule = new Schedule(rs.getLong("id"));
        schedule.setArrival(rs.getDate("arrival").toLocalDate());
        schedule.setDeparture(rs.getDate("departure").toLocalDate());
        schedule.setStatus(Schedule.RoomStatus.valueOf(rs.getString("status")));
        Room room = new Room(rs.getLong("room_id"));
        schedule.setRoom(room);
        return schedule;
    }

    @Override
    public void insertToResultSet(PreparedStatement statement, Schedule schedule) throws SQLException {
        statement.setDate(1, Date.valueOf(schedule.getArrival()));
        statement.setDate(2, Date.valueOf(schedule.getDeparture()));
        statement.setString(2, schedule.getStatus().toString());
        statement.setLong(3, schedule.getRoom().getId());
    }
}
