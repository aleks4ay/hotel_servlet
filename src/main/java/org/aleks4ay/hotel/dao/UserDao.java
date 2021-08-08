package org.aleks4ay.hotel.dao;

import org.aleks4ay.hotel.dao.mapper.UserMapper;
import org.aleks4ay.hotel.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserDao extends AbstractDao<Long, User>{
    private static final Logger log = LogManager.getLogger(UserDao.class);

    private static final String SQL_GET_ONE = "SELECT * FROM usr WHERE id = ?;";
    private static final String SQL_GET_BY_LOGIN = "SELECT * FROM usr WHERE login = ?;";
    private static final String SQL_GET_ALL = "SELECT * FROM usr;";
    private static final String SQL_DELETE = "DELETE FROM usr WHERE id = ?;";
    private static final String SQL_CREATE = "INSERT INTO usr " +
            "(name, surname, password, registered, enabled, bill, login) VALUES (?, ?, ?, ?, ?, ?, ?); ";
    private static final String SQL_UPDATE = "UPDATE usr set " +
            "name=?, surname=?, password=?, registered=?, enabled=?, bill=? WHERE login=?;";

    public UserDao(Connection connection) {
        super(connection, new UserMapper());
    }

    @Override
    public User getById(Long id) {
        return getAbstractById(SQL_GET_ONE, id);
    }

    public User getByLogin(String name) {
        try (PreparedStatement prepStatement = connection.prepareStatement(SQL_GET_BY_LOGIN)) {

            prepStatement.setString(1, name);
            ResultSet rs = prepStatement.executeQuery();
            if (rs.next()) {
                return objectMapper.extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        return findAbstractAll(SQL_GET_ALL);
    }

    public List<User> findAll(int positionOnPage, int page) {
        return findAbstractAll(positionOnPage, page, SQL_GET_ALL);
    }

/*    public Map<String, String> getLoginMap() {
        return findAll()
                .stream()
                .collect(Collectors.toMap(User::getLogin, User::getPassword));
    }*/

    @Override
    public boolean delete(Long id) {
        return deleteAbstract(SQL_DELETE, id);
    }

    @Override
    public User update(User user) {
        boolean result = updateAbstract(SQL_UPDATE, user);
        return result ? user : null;
    }

    @Override
    public User create(User user) {
        try (PreparedStatement prepStatement = connection.prepareStatement(
                SQL_CREATE, new String[]{"id", "registered", "enabled"})) {

            objectMapper.insertToResultSet(prepStatement, user);
            prepStatement.executeUpdate();

            ResultSet rs = prepStatement.getGeneratedKeys();

            if (rs.next()) {
                user.setId(rs.getLong(1));
                user.setRegistered(rs.getTimestamp(2).toLocalDateTime());
                user.setActive(rs.getBoolean(3));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user; // TODO: 02.08.2021
    }
}
