package org.aleks4ay.hotel.dao.mapper;

import org.aleks4ay.hotel.model.Role;
import org.aleks4ay.hotel.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class UserMapper implements ObjectMapper<User> {
    @Override
    public User extractFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setLogin(rs.getString("login"));
        user.setName(rs.getString("name"));
        user.setSurname(rs.getString("surname"));
        user.setPassword(rs.getString("password"));
        user.setRegistered(rs.getTimestamp("registered").toLocalDateTime());
        user.setActive(rs.getBoolean("enabled"));
        user.setBill(rs.getDouble("bill"));
        user.setRole(Role.valueOf(rs.getString("role")));
        return user;
    }

    @Override
    public void insertToResultSet(PreparedStatement statement, User user) throws SQLException {
        statement.setString(1, user.getName());
        statement.setString(2, user.getSurname());
        statement.setString(3, user.getPassword());
        statement.setTimestamp(4, Timestamp.valueOf(user.getRegistered()));
        statement.setBoolean(5, user.isActive());
        statement.setDouble(6, user.getBill());
        statement.setString(7, user.getLogin());
    }
}
