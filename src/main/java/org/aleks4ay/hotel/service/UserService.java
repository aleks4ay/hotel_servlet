package org.aleks4ay.hotel.service;

import org.aleks4ay.hotel.dao.*;
import org.aleks4ay.hotel.exception.AlreadyException;
import org.aleks4ay.hotel.model.Role;
import org.aleks4ay.hotel.model.User;
import org.aleks4ay.hotel.utils.Encrypt;

import java.sql.Connection;
import java.util.*;

public class UserService {
    private UserRoleService roleService = new UserRoleService();

    public static void main(String[] args) {
//        new UserService().create(new User(null, "alex0", "Алексей", "Сергиенко", "1313"));
        System.out.println(new UserService().getByLogin("adm"));
    }

    public User getById(Long id) {
        Connection conn = ConnectionPool.getConnection();
        UserDao userDao = new UserDao(conn);
        User user = userDao.getById(id);
        user.setRole(roleService.getById(id));
        ConnectionPool.closeConnection(conn);
        return user;
    }

    public User getByLogin(String login) {
        Connection conn = ConnectionPool.getConnection();
        UserDao userDao = new UserDao(conn);
        User user = userDao.getByLogin(login);
        user.setRole(roleService.getById(user.getId()));
        ConnectionPool.closeConnection(conn);
        return user;
    }

    public List<User> getAll() {
        Connection conn = ConnectionPool.getConnection();
        UserDao userDao = new UserDao(conn);
        List<User> users = userDao.findAll();
        Map<Long, Role> roleMap = roleService.getAllRoleAsMap();
        for (User u : users) {
            u.setRole(roleMap.get(u.getId()));
        }
        ConnectionPool.closeConnection(conn);
        return users;
    }

    public boolean delete(Long id) {
        Connection conn = ConnectionPool.getConnection();
        UserDao userDao = new UserDao(conn);
        boolean result = userDao.delete(id);
        ConnectionPool.closeConnection(conn);
        return result;
    }

    public User update(User user) {
        Connection conn = ConnectionPool.getConnection();
        UserDao userDao = new UserDao(conn);
        user = userDao.update(user);
        ConnectionPool.closeConnection(conn);
        return user;
    }

    public User create(String login, String firstName, String lastName, String pass) {
        Connection conn = ConnectionPool.getConnection();
        UserDao userDao = new UserDao(conn);
        if (checkLogin(login)) {
            throw new AlreadyException("User with login '" + login + "' already exists");
        }
        String encryptPassword = Encrypt.hash(pass, "SHA-256");
        User user = new User(0L, login, firstName, lastName, encryptPassword);
        user = userDao.create(user);
        user.setRole(Role.ROLE_USER);
        roleService.create(user.getId(), user.getRole());
        ConnectionPool.closeConnection(conn);
        return user;
    }

    public boolean checkLogin(String login) {
        Connection conn = ConnectionPool.getConnection();
        UserDao userDao = new UserDao(conn);
        boolean result = userDao.getByLogin(login) != null;
        ConnectionPool.closeConnection(conn);
        return result;
    }

    public boolean checkPassword(String login, String pass) {
        Connection conn = ConnectionPool.getConnection();
        UserDao userDao = new UserDao(conn);
        final User userFromDB = userDao.getByLogin(login);
        if (userFromDB == null) {
            ConnectionPool.closeConnection(conn);
            return false;
        }
        String passFromDb = userFromDB.getPassword();
        String encryptedPassword = Encrypt.hash(pass, "SHA-256");
        ConnectionPool.closeConnection(conn);
        return passFromDb.equals(encryptedPassword);
    }

    public List<User> getAll(int positionOnPage, int page) {
        Connection conn = ConnectionPool.getConnection();
        UserDao userDao = new UserDao(conn);
        List<User> users = userDao.findAll(positionOnPage, page);
        Map<Long, Role> roleMap = roleService.getAllRoleAsMap();
        for (User u : users) {
            u.setRole(roleMap.get(u.getId()));
        }
        ConnectionPool.closeConnection(conn);
        return users;
    }
}
