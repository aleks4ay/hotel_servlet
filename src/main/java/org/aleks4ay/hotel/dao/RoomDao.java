package org.aleks4ay.hotel.dao;

import org.aleks4ay.hotel.dao.mapper.RoomMapper;
import org.aleks4ay.hotel.model.Room;
import org.aleks4ay.hotel.model.Schedule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.List;

public class RoomDao extends AbstractDao<Long, Room>{
    private static final Logger log = LogManager.getLogger(RoomDao.class);
    private static final String SQL_GET_ONE = "SELECT * FROM room WHERE id = ?;";
    private static final String SQL_GET_ALL = "SELECT * FROM room;";
    private static final String SQL_DELETE = "DELETE FROM room WHERE id = ?;";
    private static final String SQL_CREATE = "INSERT INTO room (category, guests, description, price, number) " +
            "VALUES (?, ?, ?, ?, ?); ";
    private static final String SQL_UPDATE = "UPDATE room set category=?, guests=?, description=?, price=? " +
            "WHERE number = ?;";

    public RoomDao(Connection connection) {
        super(connection, new RoomMapper());
    }

    public static void main(String[] args) {
        RoomDao roomDao = new RoomDao(ConnectionPool.getConnection());
        Room room = roomDao.getByIdWithOccupied(5L);
        System.out.println(room);
    }

    @Override
    public Room getById(Long id) {
        return getAbstractById(SQL_GET_ONE, id);
    }


    public Room getByIdWithOccupied(long roomId) {
        Room room = getAbstractById(SQL_GET_ONE, roomId);


        // TODO: 12.08.2021
        // TODO: 12.08.2021
/*        List<Schedule> vacancies = new ScheduleDao(connection).getScheduleByRoomId(roomId);
        room.setVacancies(vacancies);
//        private static final String SQL_SELECT_OCCUPIED_ROOM = "SELECT * FROM vacancies WHERE room_id=? AND " +
//                "arrival BETWEEN ? AND ? OR departure BETWEEN ? AND ?;";*/
        return room;//
    }



    @Override
    public List<Room> findAll() {
        return findAbstractAll(SQL_GET_ALL);
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

    @Override
    public boolean delete(Long id) {
        return deleteAbstract(SQL_DELETE, id);
    }

    @Override
    public Room update(Room room) {
        boolean result = updateAbstract(SQL_UPDATE, room);
        return result ? room : null;
    }

    @Override
    public Room create(Room room) {
        return createAbstract(room, SQL_CREATE);
    }

    
}
