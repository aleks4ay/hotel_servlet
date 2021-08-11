package org.aleks4ay.hotel.dao.mapper;

import org.aleks4ay.hotel.model.*;

import java.sql.*;

public class OrderMapper implements ObjectMapper<Order> {
    @Override
    public Order extractFromResultSet(ResultSet rs) throws SQLException {
        Order order = new Order(rs.getLong("id"));
        order.setRegistered(rs.getTimestamp("registered").toLocalDateTime());
        order.setCorrectPrice(rs.getDouble("correct_price"));
        order.setStatus(Order.Status.valueOf(rs.getString("status")));
        order.setUser(new User(rs.getLong("user_id")));
        order.setRoom(new Room(rs.getLong("room_id")));
        order.setSchedule(new Schedule(rs.getLong("timetable_id")));
        return order;
    }

    @Override
    public void insertToResultSet(PreparedStatement statement, Order order) throws SQLException {
        statement.setTimestamp(1, Timestamp.valueOf(order.getRegistered()));
        statement.setDouble(2, order.getCorrectPrice());
        statement.setString(3, order.getStatus().toString());
        statement.setLong(4, order.getUser().getId());
        statement.setLong(5, order.getRoom().getId());
        statement.setLong(6, order.getSchedule().getId());
    }
}
