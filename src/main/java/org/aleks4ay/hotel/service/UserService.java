package org.aleks4ay.hotel.service;

import org.aleks4ay.hotel.dao.ConnectionPool;
import org.aleks4ay.hotel.dao.UserDao;
import org.aleks4ay.hotel.dao.Utils;
import org.aleks4ay.hotel.model.Role;
import org.aleks4ay.hotel.model.User;
import org.aleks4ay.hotel.utils.Encrypt;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserService {
    private UserDao userDao = new UserDao(ConnectionPool.getConnection());

    public User getById(Long id) {
        return userDao.getById(id);
    }

    public User getByLogin(String login) {
        return userDao.getByLogin(login);
    }

    public List<User> getAll() {
        return userDao.getAll();
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
        createUserRoles(user.getId(), user.getRoles());
        return user;
    }

    public boolean createUserRoles(long id, Set<Role> roles) {
        for (Role r : roles) {
            boolean success = userDao.createUserRoles(id, r);
            if (!success) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        new UserService().create(new User(null, "alex2", "Алексей", "Сергиенко", "1313"));
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
}
