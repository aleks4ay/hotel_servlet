package org.aleks4ay.hotel.dao;

import org.aleks4ay.hotel.dao.mapper.RoomMapper;
import org.aleks4ay.hotel.model.Room;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public class RoomDao extends AbstractDao<Long, Room>{

    public RoomDao(Connection connection) {
        super(connection, new RoomMapper());
    }

    @Override
    public Optional<Room> findById(Long id) {
        return getAbstractById("SELECT * FROM room WHERE id = ?;", id);
    }

    @Override
    public List<Room> findAll() {
        return findAbstractAll("SELECT * FROM room;");
    }

    public List<Room> findAllWithFilter(String filters) {
        String sql = "SELECT * FROM room WHERE " + filters;
        return findAbstractAll(sql);
    }

    @Override
    public Optional<Room> create(Room room) {
        String sql = "INSERT INTO room (category, guests, description, price, img_name, number) VALUES (?, ?, ?, ?, ?, ?); ";
        return createAbstract(room, sql);
    }

    public boolean update(Room room) {
        String sql = "UPDATE room set category=?, guests=?, description=?, price=?, img_name=? where number=?;";
        return updateAbstract(sql, room);
    }
}
