package org.aleks4ay.hotel.dao;

import org.aleks4ay.hotel.dao.mapper.RoomMapper;
import org.aleks4ay.hotel.model.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public class RoomDao extends AbstractDao<Long, Room>{
    private static final Logger log = LogManager.getLogger(RoomDao.class);

    public RoomDao(Connection connection) {
        super(connection, new RoomMapper());
    }

    @Override
    public Optional<Room> findById(Long id) {
        return getAbstractById("SELECT * FROM room WHERE id = ?;", id);
//        return getAbstractById("SELECT * FROM room JOIN timetable ON room.id = 21 AND room.id = room_id;", id);
    }


    @Override
    public List<Room> findAll() {
        return findAbstractAll("SELECT * FROM room;");
    }

    public List<Room> findAllWithFilter(String filters) {
        String sql = "SELECT * FROM room WHERE " + filters;
        return findAbstractAll(sql);
    }

/*    public List<Room> doFilters(List<Room> roomList, String filter) {
        return findAbstractAll(SQL_GET_ALL);
    }*/

/*    public List<Room> findAll(int positionOnPage, int page) {
        return findAbstractAll(positionOnPage, page, SQL_GET_ALL);
    }*/

//    public boolean delete(Long id) {
//        return deleteAbstract("DELETE FROM room WHERE id = ?;", id);
//    }

    public boolean update(Room room) {
        String sql = "UPDATE room set category=?, guests=?, description=?, price=? WHERE number = ?;";
        return updateAbstract(sql, room);
    }

    @Override
    public Optional<Room> create(Room room) {
        String sql = "INSERT INTO room (category, guests, description, price, number) VALUES (?, ?, ?, ?, ?); ";
        return createAbstract(room, sql);
    }
}
