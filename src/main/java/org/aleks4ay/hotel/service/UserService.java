package org.aleks4ay.hotel.service;


import org.aleks4ay.hotel.model.User;
import org.aleks4ay.hotel.run.RunDao;

import java.util.List;

public class UserService {
//    private UserDao userDao;
    private RunDao runDao = new RunDao();

    public List<User> getAll() {
        RunDao runDao = new RunDao();
        return runDao.getUsers();
    }
}
