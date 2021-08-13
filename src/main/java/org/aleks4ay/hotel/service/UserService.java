package org.aleks4ay.hotel.service;

import org.aleks4ay.hotel.dao.*;
import org.aleks4ay.hotel.exception.AlreadyException;
import org.aleks4ay.hotel.model.Role;
import org.aleks4ay.hotel.model.User;
import org.aleks4ay.hotel.utils.Encrypt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.*;

public class UserService {

    private static final Logger log = LogManager.getLogger(UserService.class);

    public static void main(String[] args) {
        new UserService().create("alex2", "Алексей", "Сергиенко", "1313");
        System.out.println(new UserService().getByLogin("alex"));
        System.out.println(new UserService().getByLogin("alex2"));
    }

    public Optional<User> getById(Long id) {
        Connection conn = ConnectionPool.getConnection();
        UserDao userDao = new UserDao(conn);
        Optional<User> userOptional = userDao.findById(id);
        ConnectionPool.closeConnection(conn);
        return userOptional;
    }

    public Optional<User> getByLogin(String login) {
        Connection conn = ConnectionPool.getConnection();
        UserDao userDao = new UserDao(conn);
        Optional<User> userOptional = userDao.getByLogin(login);
        ConnectionPool.closeConnection(conn);
        return userOptional;
    }

    public List<User> getAll() {
        Connection conn = ConnectionPool.getConnection();
        UserDao userDao = new UserDao(conn);
        List<User> users = userDao.findAll();
        ConnectionPool.closeConnection(conn);
        return users;
    }

    public boolean update(User user) {
        Connection conn = ConnectionPool.getConnection();
        UserDao userDao = new UserDao(conn);
        boolean result = userDao.update(user);
        ConnectionPool.closeConnection(conn);
        return result;
    }

    public Optional<User> create(String login, String firstName, String lastName, String pass) {
        Connection conn = ConnectionPool.getConnection();
        UserDao userDao = new UserDao(conn);
        if (checkLogin(login)) {
            throw new AlreadyException("User with login '" + login + "' already exists");
        }
        String encryptPassword = Encrypt.hash(pass, "SHA-256");
        User user = new User(0L, login, firstName, lastName, encryptPassword);
        user.setRole(Role.ROLE_USER);
        Optional<User> userOptional = userDao.create(user);
        userDao.createRole(user);
        ConnectionPool.closeConnection(conn);
        return userOptional;
    }

    public boolean checkLogin(String login) {
        Connection conn = ConnectionPool.getConnection();
        UserDao userDao = new UserDao(conn);
        boolean result = userDao.getByLogin(login).isPresent();
        ConnectionPool.closeConnection(conn);
        return result;
    }

    public boolean checkPassword(String login, String pass) {
        Connection conn = ConnectionPool.getConnection();
        UserDao userDao = new UserDao(conn);
        final Optional<User> userFromDB = userDao.getByLogin(login);
        if (userFromDB.isPresent()) {
            String passFromDb = userFromDB.get().getPassword();
            String encryptedPassword = Encrypt.hash(pass, "SHA-256");
            ConnectionPool.closeConnection(conn);
            return passFromDb.equals(encryptedPassword);
        }
        ConnectionPool.closeConnection(conn);
        return false;
    }

    public List<User> doPagination(int positionOnPage, int page, List<User> entities) {
        return new UtilService<User>().doPagination(positionOnPage, page, entities);
    }
}
