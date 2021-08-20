package org.aleks4ay.hotel.dao.mapper;

import org.aleks4ay.hotel.model.*;

import java.sql.*;

public class OrderMapper implements ObjectMapper<Order> {
    @Override
    public Order extractFromResultSet(ResultSet rs) throws SQLException {
        Order order = new Order(rs.getLong("id"));
        order.setRegistered(rs.getTimestamp("registered").toLocalDateTime());
        order.setArrival(rs.getDate("arrival").toLocalDate());
        order.setDeparture(rs.getDate("departure").toLocalDate());
        order.setGuests(rs.getInt("guests"));
        order.setCategory(Category.valueOf(rs.getString("category")));
        order.setCorrectPrice(rs.getLong("correct_price"));
        order.setPeriod(rs.getInt("period"));
        order.setStatus(Order.Status.valueOf(rs.getString("status")));
        order.setUser(new User(rs.getLong("user_id")));
        if (rs.getLong("room_id") != 0L) {
            order.setRoom(new Room(rs.getLong("room_id")));
        }
        return order;
    }

    @Override
    public void insertToResultSet(PreparedStatement statement, Order order) throws SQLException {
        statement.setTimestamp(1, Timestamp.valueOf(order.getRegistered()));
        statement.setDate(2, Date.valueOf(order.getArrival()));
        statement.setDate(3, Date.valueOf(order.getDeparture()));
        statement.setInt(4, order.getGuests());
        statement.setString(5, order.getCategory().getTitle());
        statement.setLong(6, order.getCorrectPrice());
        statement.setInt(7, order.getPeriod());
        statement.setString(8, order.getStatus().toString());
        statement.setLong(9, order.getUser().getId());
    }
}