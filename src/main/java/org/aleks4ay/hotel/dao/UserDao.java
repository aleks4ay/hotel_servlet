package org.aleks4ay.hotel.dao;

import org.aleks4ay.hotel.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserDao extends AbstractDao<User, Long>{
    private static final Logger log = LogManager.getLogger(UserDao.class);
    private static final String SQL_GET_ONE = "SELECT * FROM usr WHERE id = ?;";
    private static final String SQL_GET_BY_LOGIN = "SELECT * FROM usr WHERE login = ?;";
    private static final String SQL_GET_ALL = "SELECT * FROM usr;";
    private static final String SQL_DELETE = "DELETE FROM usr WHERE id = ?;";
//    private static final String SQL_ROLE = "INSERT INTO user_roles (user_id, role) VALUES (?, ?);";
    private static final String SQL_CREATE = "INSERT INTO usr (name, surname, password, registered, enabled, login)" +
            " VALUES (?, ?, ?, ?, ?, ?); ";
    private static final String SQL_UPDATE = "UPDATE usr set name=?, surname=?, password=?, registered=?, enabled=? " +
            "WHERE login=?;";

    public UserDao(Connection connection) {
        super(connection);
    }

    @Override
    public User getById(Long id) {
        return getAbstractById(SQL_GET_ONE, id);
    }

    public User getByLogin(String login) {
        return getAbstractByName(SQL_GET_BY_LOGIN, login);
    }

    @Override
    public List<User> getAll() {
        return getAbstractAll(SQL_GET_ALL);
    }

    public Map<String, String> getLoginMap() {
        return getAbstractAll(SQL_GET_ALL)
                .stream()
                .collect(Collectors.toMap(User::getLogin, User::getPassword));
    }

    @Override
    public boolean delete(Long id) {
        return deleteAbstract(SQL_DELETE, id);
    }

    @Override
    public User update(User user) {
        boolean result = createAbstract(SQL_UPDATE, user);
        return result ? user : null;
    }

    @Override
    public User create(User user) {
        PreparedStatement prepStatement = null;
        try {
            prepStatement = connection.prepareStatement(SQL_CREATE, new String[]{"id", "registered", "enabled"});
            fillEntityStatement(prepStatement, user);
            prepStatement.executeUpdate();

            ResultSet rs = prepStatement.getGeneratedKeys();

            if (rs.next()) {
                user.setId(rs.getLong(1));
                user.setRegistered(rs.getTimestamp(2).toLocalDateTime());
                user.setActive(rs.getBoolean(3));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.closeStatement(prepStatement);
        }
        return user; // TODO: 02.08.2021
    }


    @Override
    public User readEntity(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String login = rs.getString("login");
        String name = rs.getString("name");
        String surname = rs.getString("surname");
        String password = rs.getString("password");
        LocalDateTime registered = rs.getTimestamp("registered").toLocalDateTime();
        boolean active = rs.getBoolean("enabled");
        User user = new User(id, login, name, surname, password);
        user.setActive(active);
        user.setRegistered(registered);
        return user;
    }

    @Override
    public void fillEntityStatement(PreparedStatement statement, User user) throws SQLException {
        LocalDateTime registered = user.getRegistered();
        statement.setString(1, user.getName());
        statement.setString(2, user.getSurname());
        statement.setString(3, user.getPassword());
        statement.setTimestamp(4, Timestamp.valueOf(registered));
        statement.setBoolean(5, user.isActive());
        statement.setString(6, user.getLogin());
    }
}
