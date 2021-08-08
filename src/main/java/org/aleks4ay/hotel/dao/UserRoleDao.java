package org.aleks4ay.hotel.dao;

import org.aleks4ay.hotel.model.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

public class UserRoleDao {
    private static final Logger log = LogManager.getLogger(UserRoleDao.class);
    private static final String SQL_GET_ONE = "SELECT * FROM user_roles WHERE user_id=?;";
    private static final String SQL_GET_ALL = "SELECT * FROM user_roles;";
    private static final String SQL_DELETE = "DELETE FROM user_roles WHERE role=? and user_id=?;";
    private static final String SQL_UPDATE = "UPDATE user_roles SET role=? WHERE user_id=?;";
    private static final String SQL_CREATE = "INSERT INTO user_roles (role, user_id) VALUES (?, ?);";

    private Connection connection = null;

    public UserRoleDao(Connection connection) {
        this.connection = connection;
    }

    public Role getById(Long id) {
        Role role = null;
        try (PreparedStatement prepStatement = connection.prepareStatement(SQL_GET_ONE)) {
            prepStatement.setLong(1, id);
            ResultSet rs = prepStatement.executeQuery();
            if (rs.next()) {
                role = Role.valueOf(rs.getString("role"));
            }
        } catch (SQLException e) {
            log.warn("Exception during reading 'Role' with id = '{}'. {}", id, e);
        }
        return role;
    }

    public Map<Long, Role> getAllRoleAsMap() {
        Map<Long, Role> roles = new HashMap<>();
        try (PreparedStatement prepStatement = connection.prepareStatement(SQL_GET_ALL)) {
            ResultSet rs = prepStatement.executeQuery();
            while (rs.next()) {
                roles.put(rs.getLong("user_id"), Role.valueOf(rs.getString("role")));
            }
        } catch (SQLException e) {
            log.warn("Exception during reading all 'Role'. {}", e);
        }
        log.debug("Was read {} 'Role' for all.", roles.size());
        return roles;
    }

    public boolean delete(Long id, Role role) {
        boolean result = changeRole(id, role, SQL_DELETE);
        if (result) {
            log.debug("Was delete role '{}' for user with id = '{}'.", role, id);
        }
        return result;
    }

    public boolean createRole(long id, Role role) {
        boolean result = changeRole(id, role, SQL_CREATE);
        if (result) {
            log.debug("Was create role '{}' for user with id = '{}'.", role, id);
        }
        return result;
    }

    public boolean updateRole(long id, Role role) {
        boolean result = changeRole(id, role, SQL_UPDATE);
        if (result) {
            log.debug("Was change role to '{}' for user with id = '{}'.", role, id);
        }
        return result;
    }

    private boolean changeRole(long id, Role role, String sql) {
        int result = 0;
        try (PreparedStatement prepStatement = connection.prepareStatement(sql)) {
            prepStatement.setString(1, role.toString());
            prepStatement.setLong(2, id);
            result = prepStatement.executeUpdate();
        } catch (SQLException e) {
            log.warn("Exception during change 'Role' with id = '{}'. {}", id, e);
            e.printStackTrace();
        }
        return result == 1;
    }
}
