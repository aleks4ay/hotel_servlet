package org.aleks4ay.hotel.dao.mapper;

import org.aleks4ay.hotel.model.*;

import java.sql.*;
import java.util.Map;

public class OrderMapper implements ObjectMapper<Order> {
    @Override
    public Order extractFromResultSet(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getLong("id"));
        order.setRegistered(rs.getTimestamp("registered").toLocalDateTime());
        order.setArrival(rs.getDate("arrival").toLocalDate());
        order.setDeparture(rs.getDate("departure").toLocalDate());
        order.setCorrectPrice(rs.getDouble("correct_price"));
        order.setOrderStatus(OrderStatus.valueOf(rs.getString("status")));
        User user = new User();
        user.setId(rs.getLong("user_id"));
        order.setUser(user);
        Room room = new Room();
        room.setId(rs.getLong("room_id"));
        order.setRoom(room);
        return order;
    }

    @Override
    public Order makeUnique(Map<Long, Order> cache, Order order) {
        cache.putIfAbsent(order.getId(), order);
        return cache.get(order.getId());
    }

    @Override
    public void insertToResultSet(PreparedStatement statement, Order order) throws SQLException {
        statement.setTimestamp(1, Timestamp.valueOf(order.getRegistered()));
        statement.setDate(2, Date.valueOf(order.getArrival()));
        statement.setDate(3, Date.valueOf(order.getDeparture()));
        statement.setDouble(4, order.getCorrectPrice());
        statement.setString(5, order.getOrderStatus().toString());
        statement.setLong(6, order.getUser().getId());
        statement.setLong(7, order.getRoom().getId());
    }
}
