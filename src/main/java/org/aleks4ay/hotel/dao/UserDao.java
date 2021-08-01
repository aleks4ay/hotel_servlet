package org.aleks4ay.hotel.dao;

import org.aleks4ay.hotel.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserDao extends AbstractDao<User, Long>{
    private static final Logger log = LoggerFactory.getLogger(UserDao.class);
    private static final String SQL_GET_ONE = "SELECT * FROM usr WHERE id = ?;";
    private static final String SQL_GET_BY_LOGIN = "SELECT * FROM usr WHERE login = ?;";
    private static final String SQL_GET_ALL = "SELECT * FROM usr;";
//    private static final String SQL_LOGINS = "SELECT login, password FROM usr;";
    private static final String SQL_DELETE = "DELETE FROM usr WHERE id = ?;";
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
    public boolean create(User user) {
        return createAbstract(SQL_CREATE, user);
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
