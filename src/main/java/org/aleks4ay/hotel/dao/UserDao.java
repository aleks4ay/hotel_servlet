package org.aleks4ay.hotel.dao;

import org.aleks4ay.hotel.dao.mapper.UserMapper;
import org.aleks4ay.hotel.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class UserDao extends AbstractDao<Long, User>{
    private static final Logger log = LogManager.getLogger(UserDao.class);

    public UserDao(Connection connection) {
        super(connection, new UserMapper());
    }

    @Override
    public Optional<User> findById(Long id) {
        return getAbstractById("SELECT * FROM usr JOIN user_roles ON id = ? AND usr.id=user_id;", id);
    }

    public Optional<User> findByLogin(String name) {
        String SQL_GET_BY_LOGIN = "SELECT * FROM usr INNER JOIN user_roles ON login = ? AND usr.id=user_id;";
        try (PreparedStatement prepStatement = connection.prepareStatement(SQL_GET_BY_LOGIN)) {
            prepStatement.setString(1, name);
            ResultSet rs = prepStatement.executeQuery();
            if (rs.next()) {
                return Optional.of(objectMapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<User> findByLoginAndPassword(String name, String pass) {
        String sql = "SELECT * FROM usr INNER JOIN user_roles ON login = ? AND password = ? AND usr.id=user_id;";
        try (PreparedStatement prepStatement = connection.prepareStatement(sql)) {
            prepStatement.setString(1, name);
            prepStatement.setString(2, pass);
            ResultSet rs = prepStatement.executeQuery();
            if (rs.next()) {
                return Optional.of(objectMapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll(String sortMethod) {
        return findAbstractAll("SELECT * FROM usr JOIN user_roles ON usr.id=user_id order by " + sortMethod + ";");
    }

    public boolean update(User user) {
        String sql = "UPDATE usr set name=?, surname=?, password=?, registered=?, active=?, bill=? WHERE login=?;";
        return updateAbstract(sql, user);
    }

    public boolean updateRole(User user) {
        String sql = "UPDATE user_roles set role=? WHERE user_id=?;";
        return updateStringAbstract(user.getRole().toString(), user.getId(), sql);
    }

    public boolean createRole(User user) {
        int result = 0;
        String sql = "INSERT INTO user_roles (role, user_id) VALUES (?, ?);";
        try (PreparedStatement prepStatement = connection.prepareStatement(sql)) {
            prepStatement.setString(1, user.getRole().toString());
            prepStatement.setLong(2, user.getId());
            result = prepStatement.executeUpdate();
        } catch (SQLException e) {
            log.warn("Exception during change 'Role' with id = '{}'. {}", user.getId(), e);
            e.printStackTrace();
        }
        return result == 1;
    }

    @Override
    public Optional<User> create(User user) {
        Optional<User> result = Optional.empty();
        String sql = "INSERT INTO usr (name, surname, password, registered, active, bill, login) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?); ";
        try (PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id", "registered", "active"})) {

            objectMapper.insertToResultSet(ps, user);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                user.setId(rs.getLong(1));
                user.setRegistered(rs.getTimestamp(2).toLocalDateTime());
                user.setActive(rs.getBoolean(3));
                result = Optional.of(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
