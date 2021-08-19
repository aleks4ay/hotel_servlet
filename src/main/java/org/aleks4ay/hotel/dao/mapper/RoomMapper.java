package org.aleks4ay.hotel.dao.mapper;

import org.aleks4ay.hotel.model.Category;
import org.aleks4ay.hotel.model.Room;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomMapper implements ObjectMapper<Room> {
    @Override
    public Room extractFromResultSet(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setId(rs.getLong("id"));
        room.setNumber(rs.getInt("number"));
        room.setCategory(Category.valueOf(rs.getString("category")));
        room.setGuests(rs.getInt("guests"));
        room.setDescription(rs.getString("description"));
        room.setImgName(rs.getString("img_name"));
        room.setPrice(rs.getLong("price"));
        return room;
    }

    @Override
    public void insertToResultSet(PreparedStatement statement, Room room) throws SQLException {
        statement.setString(1, room.getCategory().getTitle());
        statement.setInt(2, room.getGuests());
        statement.setString(3, room.getDescription());
        statement.setDouble(4, room.getPrice());
        statement.setString(5, room.getImgName());
        statement.setInt(6, room.getNumber());
    }
}
