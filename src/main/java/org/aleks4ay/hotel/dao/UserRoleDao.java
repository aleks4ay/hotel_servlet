package org.aleks4ay.hotel.dao;

import org.aleks4ay.hotel.model.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

public class UserRoleDao {
    private static final Logger log = LogManager.getLogger(UserRoleDao.class);
    private static final String SQL_GET_ONE = "SELECT * FROM user_roles WHERE user_id = ?;";
    private static final String SQL_GET_ALL = "SELECT * FROM user_roles;";
    private static final String SQL_DELETE = "DELETE FROM user_roles WHERE user_id = ? and role = ?;";
    private static final String SQL_CREATE = "INSERT INTO user_roles (user_id, role) VALUES (?, ?);";

    private Connection connection = null;

    public UserRoleDao(Connection connection) {
        this.connection = connection;
    }

    public Set<Role> getById(Long id) {
        Set<Role> roles = new HashSet<>();
        ResultSet rs = null;
        PreparedStatement prepStatement = null;
        try {
            prepStatement = connection.prepareStatement(SQL_GET_ONE);
            prepStatement.setLong(1, id);
            rs = prepStatement.executeQuery();
            while (rs.next()) {
                Role role = Role.valueOf(rs.getString("role"));
                roles.add(role);
            }
        } catch (SQLException e) {
            log.warn("Exception during reading 'Role' with id = '{}'. {}", id, e);
        } finally {
            ConnectionPool.closeResultSet(rs);
            ConnectionPool.closeStatement(prepStatement);
        }
        log.debug("Was read {} 'Role' for user with id = '{}'.", roles.size(), id);
        return roles;
    }

    public Map<Long, Set<Role>> getAllRoleAsMap() {
        Map<Long, Set<Role>> roles = new HashMap<>();
        ResultSet rs = null;
        PreparedStatement prepStatement = null;
        try {
            prepStatement = connection.prepareStatement(SQL_GET_ALL);
            rs = prepStatement.executeQuery();
            while (rs.next()) {
                long userId = rs.getLong("user_id");
                Role role = Role.valueOf(rs.getString("role"));
                if (roles.containsKey(userId)) {
                    roles.get(userId).add(role);
                } else {
                    Set<Role> tempRoles = new HashSet<>();
                    tempRoles.add(role);
                    roles.put(userId, tempRoles);
                }
            }
        } catch (SQLException e) {
            log.warn("Exception during reading 'Roles'. {}", e);
        } finally {
            ConnectionPool.closeResultSet(rs);
            ConnectionPool.closeStatement(prepStatement);
        }
        log.debug("Was read {} 'Roles' for all.", roles.size());
        return roles;
    }

    public boolean delete(Long id, Role role) {
        boolean result = updateRole(id, role, SQL_DELETE);
        if (result) {
            log.debug("Was delete {} for user with id = '{}'.", role, id);
        }
        return result;
    }

    public boolean createRole(long id, Role role) {
        boolean result = updateRole(id, role, SQL_CREATE);
        if (result) {
            log.debug("Was create {} for user with id = '{}'.", role, id);
        }
        return result;
    }

    private boolean updateRole(long id, Role role, String sql) {
        PreparedStatement prepStatement = null;
        int result = 0;
        try {
            prepStatement = connection.prepareStatement(sql);
            prepStatement.setLong(1, id);
            prepStatement.setString(2, role.getTitle());
            result = prepStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.closeStatement(prepStatement);
        }
        return result == 1;
    }
}
