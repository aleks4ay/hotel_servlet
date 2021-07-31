package org.aleks4ay.hotel.service;


import org.aleks4ay.hotel.model.Room;
import org.aleks4ay.hotel.run.RunDao;

import java.util.List;

public class RoomService {
//    private UserDao userDao;
    private RunDao runDao = new RunDao();

    public List<Room> getAll() {
        RunDao runDao = new RunDao();
        return runDao.getRooms();
    }
}
