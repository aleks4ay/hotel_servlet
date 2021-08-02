package org.aleks4ay.hotel.service;

import org.aleks4ay.hotel.dao.ConnectionPool;
import org.aleks4ay.hotel.dao.UserRoleDao;
import org.aleks4ay.hotel.model.Role;

import java.util.*;

public class UserRoleService {
    private UserRoleDao roleDao = new UserRoleDao(ConnectionPool.getConnection());

    public static void main(String[] args) {
        new UserRoleService().create(4L, Role.ROLE_USER);
        new UserRoleService().create(5L, Role.ROLE_USER);
        new UserRoleService().create(6L, Role.ROLE_USER);
        new UserRoleService().create(10L, Role.ROLE_USER);
    }

    public Set<Role> getById(Long id) {
        return roleDao.getById(id);
    }

    public Map<Long, Set<Role>> getAllRoleAsMap() {
        return roleDao.getAllRoleAsMap();
    }

    public boolean delete(Long id, Role role) {
        return roleDao.delete(id, role);
    }

    public boolean create(long id, Role role) {
        return roleDao.createRole(id, role);
    }

    public boolean createRoles(long id, Set<Role> roles) {
        for (Role r : roles) {
            boolean success = create(id, r);
            if (!success) {
                return false;
            }
        }
        return true;
    }
}
