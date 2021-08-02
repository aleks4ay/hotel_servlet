package org.aleks4ay.hotel.service;

import org.aleks4ay.hotel.dao.ConnectionPool;
import org.aleks4ay.hotel.dao.RoomDao;
import org.aleks4ay.hotel.model.Category;
import org.aleks4ay.hotel.model.Room;

import java.util.List;

public class RoomService {
    private RoomDao dao = new RoomDao(ConnectionPool.getConnection());

    public static void main(String[] args) {
        Room room1 = new Room(1, 101, Category.STANDARD, 2, "Номер с видом на море", 1_600.0);
        Room room2 = new Room(2, 106, Category.SUPERIOR, 3, "Номер с wi-fi и видом на море", 2_800.0);
        Room room3 = new Room(3, 501, Category.DELUXE, 5, "Люкс с бассейном", 100_000.0);
        RoomService roomService = new RoomService();
        roomService.create(room1);
        roomService.create(room2);
        roomService.create(room3);
    }

    public Room getById(Long id) {
        return dao.getById(id);
    }

    public List<Room> getAll() {
        return dao.getAll();
    }

    public boolean delete(Long id) {
        return dao.delete(id);
    }

    public Room update(Room room) {
        return dao.update(room);
    }

    public Room create(Room room) {
        return dao.create(room);
    }
}
