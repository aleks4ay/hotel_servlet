package org.aleks4ay.hotel.dao.mapper;

import org.aleks4ay.hotel.dao.mapper.ObjectMapper;
import org.aleks4ay.hotel.model.Category;
import org.aleks4ay.hotel.model.Room;

import java.sql.*;
import java.util.Map;

public class RoomMapper implements ObjectMapper<Room> {
    @Override
    public Room extractFromResultSet(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setId(rs.getLong("id"));
        room.setNumber(rs.getInt("number"));
        room.setCategory(Category.valueOf(rs.getString("category")));
        room.setGuests(rs.getInt("guests"));
        room.setDescription(rs.getString("description"));
        room.setPrice(rs.getDouble("price"));
        return room;
    }

    @Override
    public Room makeUnique(Map<Long, Room> cache, Room room) {
        cache.putIfAbsent(room.getId(), room);
        return cache.get(room.getId());
    }

    @Override
    public void insertToResultSet(PreparedStatement statement, Room room) throws SQLException {
        statement.setString(1, room.getCategory().getTitle());
        statement.setInt(2, room.getGuests());
        statement.setString(3, room.getDescription());
        statement.setDouble(4, room.getPrice());
        statement.setInt(5, room.getNumber());
    }
}
