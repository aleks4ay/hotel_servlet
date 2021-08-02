package org.aleks4ay.hotel.service;

import org.aleks4ay.hotel.dao.*;
import org.aleks4ay.hotel.model.Role;
import org.aleks4ay.hotel.model.User;
import org.aleks4ay.hotel.utils.Encrypt;

import java.util.*;

public class UserService {
    private UserDao userDao = new UserDao(ConnectionPool.getConnection());
    private UserRoleService roleService = new UserRoleService();

    public static void main(String[] args) {
//        new UserService().create(new User(null, "alex0", "Алексей", "Сергиенко", "1313"));
        new UserService().getAll().forEach(System.out::println);
    }

    public User getById(Long id) {
        User user = userDao.getById(id);
        user.setRoles(roleService.getById(id));
        return user;
    }

    public User getByLogin(String login) {
        User user = userDao.getByLogin(login);
        user.setRoles(roleService.getById(user.getId()));
        return user;
    }

    public List<User> getAll() {
        List<User> users = userDao.getAll();
        Map<Long, Set<Role>> roleMap = roleService.getAllRoleAsMap();
        for (User u : users) {
            u.setRoles(roleMap.get(u.getId()));
        }
        return users;
    }

    public boolean delete(Long id) {
        return userDao.delete(id);
    }

    public User update(User user) {
        return userDao.update(user);
    }

    public User create(User user) {
        Map<String, String> loginMap = userDao.getLoginMap();
        if (loginMap.keySet().contains(user.getLogin())) {
            return null;
        }
        String encryptPassword = Encrypt.hash(user.getPassword(), "SHA-256");
        user.setPassword(encryptPassword);
        user.addRole(Role.ROLE_USER);
        user = userDao.create(user);
        roleService.createRoles(user.getId(), user.getRoles());
        return user;
    }

    public boolean checkLogin(String login) {
        Map<String, String> loginMap = userDao.getLoginMap();
        if (loginMap.keySet().contains(login)) {
            return true;
        }
        return false;
    }

    public boolean checkPassword(String login, String pass) {
        Map<String, String> loginMap = userDao.getLoginMap();
        if (loginMap.keySet().contains(login)) {
            String passFromDb = loginMap.get(login);
            String encriptedPassword = Encrypt.hash(pass, "SHA-256");
            if (passFromDb.equals(encriptedPassword)) {
                return true;
            }
        }
        return false;
    }

    public List<User> getAll(int positionOnPage, int page) {
        int startPosition = positionOnPage * (page - 1);
        List<User> users = getAll();
        List<User> usersAfterFilter = new ArrayList<>();

        if (users.size() > startPosition) {
            for (int i = startPosition; i < startPosition + positionOnPage; i++) {
                if (i >= users.size()) {
                    break;
                }
                usersAfterFilter.add(users.get(i));
            }
            return usersAfterFilter;
        }
        return new ArrayList<>();
    }
}
