package org.aleks4ay.hotel.dao;

import org.aleks4ay.hotel.model.Category;
import org.aleks4ay.hotel.model.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.List;

public class RoomDao extends AbstractDao<Room, Long>{
    private static final Logger log = LogManager.getLogger(RoomDao.class);
    private static final String SQL_GET_ONE = "SELECT * FROM room WHERE id = ?;";
    private static final String SQL_GET_ALL = "SELECT * FROM room;";
    private static final String SQL_DELETE = "DELETE FROM room WHERE id = ?;";
    private static final String SQL_CREATE = "INSERT INTO room (category, guests, description, prise, number) " +
            "VALUES (?, ?, ?, ?, ?); ";
    private static final String SQL_UPDATE = "UPDATE room set category=?, guests=?, description=?, prise=? " +
            "WHERE number = ?;";

    public RoomDao(Connection connection) {
        super(connection);
    }

    @Override
    public Room getById(Long id) {
        return getAbstractById(SQL_GET_ONE, id);
    }

    @Override
    public List<Room> getAll() {
        return getAbstractAll(SQL_GET_ALL);
    }

    @Override
    public boolean delete(Long id) {
        return deleteAbstract(SQL_DELETE, id);
    }

    @Override
    public Room update(Room room) {
        boolean result = createAbstract(SQL_UPDATE, room);
        return result ? room : null;
    }

    @Override
    public Room create(Room room) {
        PreparedStatement prepStatement = null;
        try {
            prepStatement = connection.prepareStatement(SQL_CREATE, new String[]{"id"});
            fillEntityStatement(prepStatement, room);
            prepStatement.executeUpdate();

            ResultSet rs = prepStatement.getGeneratedKeys();

            if (rs.next()) {
                room.setId(rs.getLong(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.closeStatement(prepStatement);
        }
        return room;
    }

    @Override
    public Room readEntity(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        int number = rs.getInt("number");
        String categoryName = rs.getString("category");
        Category category = Category.valueOf(categoryName);
        int guests = rs.getInt("guests");
        String description = rs.getString("description");
        double prise = rs.getDouble("prise");
        return new Room(id, number, category, guests, description, prise);
    }

    @Override
    public void fillEntityStatement(PreparedStatement statement, Room room) throws SQLException {
        statement.setString(1, room.getCategory().getTitle());
        statement.setInt(2, room.getGuests());
        statement.setString(3, room.getDescription());
        statement.setDouble(4, room.getPrice());
        statement.setInt(5, room.getNumber());
    }
}
