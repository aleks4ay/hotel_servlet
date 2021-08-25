package org.aleks4ay.hotel.dao;

import org.aleks4ay.hotel.dao.mapper.RoomMapper;
import org.aleks4ay.hotel.model.Room;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RoomDao extends AbstractDao<Long, Room>{

    public RoomDao(Connection connection) {
        super(connection, new RoomMapper());
    }

    @Override
    public Optional<Room> findById(Long id) {
        return getAbstractById("SELECT * FROM room WHERE id = ?;", id);
    }

    public Optional<Room> findByNumber(int number) {
        try (PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM room WHERE number = ?;")) {
            prepStatement.setInt(1, number);
            ResultSet rs = prepStatement.executeQuery();
            if (rs.next()) {
                return Optional.of(objectMapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Room> findAll(String sortMethod) {
        return findAbstractAll("SELECT * FROM room order by " + sortMethod + ";");
    }

    public List<Room> findEmptyRoom(LocalDate start, LocalDate end, String sortMethod, List<String> filters) {
        String filterSql = filters.stream()
                .map(s -> " and " + s)
                .collect(Collectors.joining());

        String sql2 = "select r.* from room r WHERE r.id NOT IN (" +
                "           select DISTINCT x.room_id from order_room x INNER JOIN orders o on x.order_id = o.id " +
                "           and o.status != 'CANCEL' and o.status != 'NEW' " +
                "and (" +
                "           (? BETWEEN o.arrival and o.departure)" +
                "        or (? BETWEEN o.arrival and o.departure)" +
                "        or (o.arrival BETWEEN ? and ?))) " +
                filterSql +
                " order by " + sortMethod + ";";
        List<Room> roomList = new ArrayList<>();
        try (PreparedStatement prepStatement = connection.prepareStatement(sql2)){
            prepStatement.setDate(1, Date.valueOf(start));
            prepStatement.setDate(2, Date.valueOf(end));
            prepStatement.setDate(3, Date.valueOf(start));
            prepStatement.setDate(4, Date.valueOf(end));
            ResultSet rs = prepStatement.executeQuery();
            while (rs.next()) {
                roomList.add(objectMapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomList;
    }

    public List<Room> findEmptyRoom(LocalDate start, String sortMethod, List<String> filters) {
        return findEmptyRoom(start, start, sortMethod, filters);
    }


    public List<Room> findAllWithFilter(String sortMethod, List<String> filters) {
        String filterSql = filters.stream()
                .map(s -> " and " + s)
                .collect(Collectors.joining()).replaceFirst("and", "where");
        String sql = "SELECT * FROM room " + filterSql + " order by " + sortMethod + ";";
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
