package org.aleks4ay.hotel.service;

import org.aleks4ay.hotel.dao.*;
import org.aleks4ay.hotel.exception.AlreadyException;
import org.aleks4ay.hotel.exception.NotFoundException;
import org.aleks4ay.hotel.model.Role;
import org.aleks4ay.hotel.model.User;
import org.aleks4ay.hotel.utils.Encrypt;

import java.sql.Connection;
import java.util.*;
import java.util.stream.Collectors;

public class UserService {

    private final ConnectionPool connectionPool;

    public UserService(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public User findById(Long id) {
        Connection conn = connectionPool.getConnection();
        Optional<User> userOptional = new UserDao(conn).findById(id);
        connectionPool.closeConnection(conn);
        return userOptional.orElseThrow(() -> new NotFoundException("User with id = " + id + " non found"));
    }

    public User findByLogin(String login) {
        Connection conn = connectionPool.getConnection();
        Optional<User> userOptional = new UserDao(conn).findByLogin(login);
        connectionPool.closeConnection(conn);
        return userOptional.orElseThrow(() -> new NotFoundException("User '" + login + "' non found"));
    }

    public User findByLoginAndPassword(String login, String pass) {
        Connection conn = connectionPool.getConnection();
        Optional<User> userOptional = new UserDao(conn).findByLoginAndPassword(login, Encrypt.hash(pass, "SHA-256"));
        connectionPool.closeConnection(conn);
        return userOptional.orElseThrow(() -> new NotFoundException("User '" + login + "' non found or password is wrong"));
    }

    public List<User> findAll(String sortMethod) {
        Connection conn = connectionPool.getConnection();
        List<User> users = new UserDao(conn).findAll(sortMethod);
        connectionPool.closeConnection(conn);
        return users;
    }

    Map<Long, User> getAllAsMap(String sortMethod) {
        return findAll(sortMethod).stream()
                .collect(Collectors.toMap(User::getId, u -> u));
    }

    public User save(User user) {
        Connection conn = connectionPool.getConnection();
        user.setPassword(Encrypt.hash(user.getPassword(), "SHA-256"));
        user.setRole(Role.ROLE_USER);
        user.addBill(0d);
        User result = new UserDao(conn).create(user)
                    .orElseThrow(() -> new AlreadyException("User with login '" + user.getLogin() + "' already exists"));
        new UserDao(conn).createRole(user);
        connectionPool.closeConnection(conn);
        return result;
    }

    public boolean update(User user) {
        Connection conn = connectionPool.getConnection();
        boolean result = new UserDao(conn).update(user);
        connectionPool.closeConnection(conn);
        return result;
    }

    public List<User> doPagination(int positionOnPage, int page, List<User> entities) {
        return new UtilService<User>().doPagination(positionOnPage, page, entities);
    }
}
