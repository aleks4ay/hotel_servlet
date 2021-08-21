package org.aleks4ay.hotel.dao;

import org.aleks4ay.hotel.dao.mapper.OrderMapper;
import org.aleks4ay.hotel.model.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class OrderDao extends AbstractDao<Long, Order>{
    private static final Logger log = LogManager.getLogger(OrderDao.class);

    public OrderDao(Connection connection) {
        super(connection, new OrderMapper());
    }

    @Override
    public Optional<Order> findById(Long id) {
        return getAbstractById("SELECT * FROM (SELECT * from orders o left join order_room x on o.id = x.order_id) " +
                " AS o2 WHERE id = ?;", id);
    }

    @Override
    public List<Order> findAll(String sortMethod) {
        return findAbstractAll("SELECT o.*, x.room_id FROM orders  o left join order_room x on o.id = x.order_id " +
                " order by " + sortMethod + ";");
    }

    public List<Order> findAllByUser(long userId, String sortMethod) {
        return findAbstractAllById(userId, "SELECT * FROM (SELECT * from orders o left join order_room x " +
                "on o.id = x.order_id)  AS o2 WHERE user_id = ? order by " + sortMethod + ";");
    }

    @Override
    public Optional<Order> create(Order order) {
        Optional<Order> newOrder = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO orders (registered, arrival, " +
                        "departure, guests, category, correct_price, period, status, user_id) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);",
                new String[]{"id"}) ) {

            objectMapper.insertToResultSet(statement, order);

            statement.executeUpdate();
            ResultSet rs3 = statement.getGeneratedKeys();
            if (rs3.next()) {
                order.setId(rs3.getLong(1));
                newOrder = Optional.of(order);
            } else {
                log.info("Can't insert new Order to DB. {}", order);
            }
        } catch (SQLException e) {
            log.warn("Exception during create order '{}'. {}", order, e);
        }
        return newOrder;
    }

    public int checkRoomByRoomId(Long roomId, LocalDate start, LocalDate end) {
        try (PreparedStatement statement = connection.prepareStatement(
                "select count(o.*) from order_room x INNER JOIN orders o on x.order_id = o.id AND x.room_id = ? " +
                "         and ( (? BETWEEN o.arrival and o.departure) " +
                "            or (? BETWEEN o.arrival and o.departure) " +
                "            or (o.arrival BETWEEN ? and ?) )")) {

            statement.setLong(1, roomId);
            statement.setDate(2, Date.valueOf(start));
            statement.setDate(3, Date.valueOf(end));
            statement.setDate(4, Date.valueOf(start));
            statement.setDate(5, Date.valueOf(end));
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            log.warn("Exception during check room with id '{}'. {}", roomId, e);
        }
        return 0;
    }

    public void saveRoomLink(Order order) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO order_room (order_id, room_id) " +
                        "VALUES (?, ?);") ) {
            statement.setLong(1, order.getId());
            statement.setLong(2, order.getRoom().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.warn("Exception during create order-room link '{}'. {}", order, e);
        }
    }

    public boolean updateStatus(Order o) {
        return updateStringAbstract(o.getStatus().toString(), o.getId(),
                "update orders set status = ? where status !='CANCEL' and id = ?;");
    }

    public void updateRoom(Order order) {
        try (PreparedStatement statement = connection.prepareStatement("update orders set status = 'BOOKED', " +
                "correct_price = ? where id = ? and status = 'NEW';") ) {
            statement.setLong(1, order.getRoom().getPrice());
            statement.setLong(2, order.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.warn("Exception during create order-room link '{}'. {}", order, e);
        }
    }

    public void doInvoiceInspector() {
        try (PreparedStatement statement = connection.prepareStatement(
                "update invoice set status = 'CANCEL' where status = 'NEW' and registered < ?; " +
                " update orders set status = 'CANCEL' where status = 'CONFIRMED' and id in (" +
                     "select x.order_id from order_invoice x inner join invoice i on x.invoice_id = i.id " +
                     "and i.status = 'CANCEL');") ) {
            LocalDateTime canceledDate = LocalDateTime.now().minusDays(2);
            statement.setTimestamp(1, Timestamp.valueOf(canceledDate));
            statement.executeUpdate();
        } catch (SQLException e) {
            log.warn("Exception during cancel old Invoice.", e);
        }
    }
}
