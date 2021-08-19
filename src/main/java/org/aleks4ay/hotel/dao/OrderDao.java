package org.aleks4ay.hotel.dao;

import org.aleks4ay.hotel.dao.mapper.OrderMapper;
import org.aleks4ay.hotel.model.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class OrderDao extends AbstractDao<Long, Order>{
    private static final Logger log = LogManager.getLogger(OrderDao.class);

    public OrderDao(Connection connection) {
        super(connection, new OrderMapper());
    }

    @Override
    public Optional<Order> findById(Long id) {
        return getAbstractById("SELECT o.*, x.room_id FROM orders o left join order_room x on o.id = x.order_id " +
                "and o.id = ?;", id);
    }

    @Override
    public List<Order> findAll() {
        return findAbstractAll("SELECT o.*, x.room_id FROM orders  o left join order_room x on o.id = x.order_id");
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
}
