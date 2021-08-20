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

    private ConnectionPool connectionPool;

    public UserService(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public User getById(Long id) {
        Connection conn = connectionPool.getConnection();
        Optional<User> userOptional = new UserDao(conn).findById(id);
        connectionPool.closeConnection(conn);
        return userOptional.orElseThrow(() -> new NotFoundException("User with id = " + id + " non found"));
    }

    public User getByLogin(String login) {
        Connection conn = connectionPool.getConnection();
        Optional<User> userOptional = new UserDao(conn).getByLogin(login);
        connectionPool.closeConnection(conn);
        return userOptional.orElseThrow(() -> new NotFoundException("User '" + login + "' non found"));
    }

    public User getByLoginAndPassword(String login, String pass) {
        Connection conn = connectionPool.getConnection();
        Optional<User> userOptional = new UserDao(conn).getByLoginAndPassword(login, Encrypt.hash(pass, "SHA-256"));
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

    public void addOldValues(Map<String, Object> model, User user) {
        model.put("wrongLogin", "User exists!");
        model.put("oldLogin", user.getLogin());
        model.put("oldFirstName", user.getName());
        model.put("oldLastName", user.getSurname());
    }

/*    public boolean checkPassword(String login, String pass) {
        Connection conn = connectionPool.getConnection();
        final Optional<User> userFromDB = new UserDao(conn).getByLogin(login);
        if (userFromDB.isPresent()) {
            String passFromDb = userFromDB.get().getPassword();
            String encryptedPassword = Encrypt.hash(pass, "SHA-256");
            connectionPool.closeConnection(conn);
            return passFromDb.equals(encryptedPassword);
        }
        connectionPool.closeConnection(conn);
        return false;
    }*/

    public List<User> doPagination(int positionOnPage, int page, List<User> entities) {
        return new UtilService<User>().doPagination(positionOnPage, page, entities);
    }



    /*public Optional<User> create(String login, String firstName, String lastName, String pass) {
        Connection conn = ConnectionPoolProduction.getConnection();
        UserDao userDao = new UserDao(conn);
        if (checkLogin(login)) {
            throw new AlreadyException("User with login '" + login + "' already exists");
        }
        String encryptPassword = Encrypt.hash(pass, "SHA-256");
        User user = new User(0L, login, firstName, lastName, encryptPassword);
        user.setRole(Role.ROLE_USER);
        Optional<User> userOptional = userDao.create(user);
        userDao.createRole(user);
        ConnectionPoolProduction.closeConnection(conn);
        return userOptional;
    }

    public boolean checkLogin(String login) {
        Connection conn = ConnectionPoolProduction.getConnection();
        boolean result = new UserDao(conn).getByLogin(login).isPresent();
        ConnectionPoolProduction.closeConnection(conn);
        return result;
    }*/
}
