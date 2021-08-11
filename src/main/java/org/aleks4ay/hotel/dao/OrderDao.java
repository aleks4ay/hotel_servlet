package org.aleks4ay.hotel.dao;

import org.aleks4ay.hotel.dao.mapper.OrderMapper;
import org.aleks4ay.hotel.model.Order;
import org.aleks4ay.hotel.model.Room;
import org.aleks4ay.hotel.model.Schedule;
import org.aleks4ay.hotel.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDao extends AbstractDao<Long, Order>{
    private static final Logger log = LogManager.getLogger(OrderDao.class);

    public OrderDao(Connection connection) {
        super(connection, new OrderMapper());
    }

    public static void main(String[] args) {
        OrderDao dao;
        Connection connection;
        connection = ConnectionPool.getConnection();
        dao = new OrderDao(connection);

        Room room = new RoomDao(connection).getById(21L);
        Order tempOrder = new Order(room, LocalDateTime.now());
        User user = new UserDao(connection).getByLogin("rt");
//        tempOrder.setUser(user);
        user.addOrder(tempOrder);
        Schedule schedule = new Schedule(LocalDate.of(2021, 8, 17), LocalDate.of(2021, 8, 20), Schedule.RoomStatus.BOOKED, room);
        tempOrder.setSchedule(schedule);
        Order actual = dao.create(tempOrder);
        System.out.println(actual);
    }

    @Override
    public Order getById(Long id) { // TODO: 12.08.2021 lizi
        return getAbstractById("SELECT * FROM orders WHERE id = ?;", id);
    }

    @Override
    public List<Order> findAll() {
        return findAbstractAll("SELECT * FROM orders;");
    }

    @Override
    public boolean delete(Long id) {
        return deleteAbstract("DELETE FROM orders WHERE id = ?;", id);
    }

    @Override
    public Order update(Order room) {
        return null;
    }

    @Override
    public Order create(Order order) {
        boolean result = false;
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement("SELECT count(*) FROM timetable WHERE room_id=? AND " +
                    "(arrival BETWEEN ? AND ? OR departure BETWEEN ? AND ?);");
            ps.setLong(1, order.getRoom().getId());
            ps.setDate(2, Date.valueOf(order.getSchedule().getArrival()));
            ps.setDate(4, Date.valueOf(order.getSchedule().getArrival()));

            ps.setDate(3, Date.valueOf(order.getSchedule().getDeparture()));
            ps.setDate(5, Date.valueOf(order.getSchedule().getDeparture()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getInt(1) > 0) {
                    result = false;
                    log.info("Selected room #{} already occupied.", order.getRoom().getNumber());
                } else {
                    result = true;
                }
            }

            if (result) {
                PreparedStatement ps2 = connection.prepareStatement(
                        "INSERT INTO timetable (arrival, departure, status, room_id) VALUES (?, ?, ?, ?);"
                        , new String[]{"id"});
                ps2.setDate(1, Date.valueOf(order.getSchedule().getArrival()));
                ps2.setDate(2, Date.valueOf(order.getSchedule().getDeparture()));
                ps2.setString(3, order.getSchedule().getStatus().toString());
                ps2.setLong(4, order.getRoom().getId());
                ps2.executeUpdate();
                ResultSet rs2 = ps2.getGeneratedKeys();
                if (rs2.next()) {
                    long scheduleId = rs2.getLong(1);
                    order.getSchedule().setId(scheduleId);
                    result = true;
                } else {
                    log.info("Can't insert new Schedule to DB. {}", order.getSchedule());
                    result = false;
                }
            }

            if (result) {
                PreparedStatement ps3 = connection.prepareStatement("INSERT INTO orders (registered, correct_price," +
                                " status, user_id, room_id, timetable_id) VALUES (?, ?, ?, ?, ?, ?); "
                        , new String[]{"id"});
                objectMapper.insertToResultSet(ps3, order);

                ps3.executeUpdate();
                ResultSet rs3 = ps3.getGeneratedKeys();
                if (rs3.next()) {
                    order.setId(rs3.getLong(1));
                    result = true;
                } else {
                    log.info("Can't insert new Order to DB. {}", order);
                    result = false;
                }
            }

            if (result) {
                connection.commit();
            } else {
                connection.rollback();
            }
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result ? order : null;
    }

    public boolean updateStatus(String s, long id) {
        return updateStringAbstract(s, id, "UPDATE orders SET status=? WHERE id=?;");
    }

}
