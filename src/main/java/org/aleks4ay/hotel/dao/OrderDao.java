package org.aleks4ay.hotel.dao;

import org.aleks4ay.hotel.dao.mapper.OrderMapper;
import org.aleks4ay.hotel.dao.mapper.RoomMapper;
import org.aleks4ay.hotel.model.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;

public class OrderDao extends AbstractDao<Long, Order>{
    private static final Logger log = LogManager.getLogger(OrderDao.class);
    private static final String SQL_GET_ONE = "SELECT * FROM orders WHERE id = ?;";
    private static final String SQL_GET_ALL = "SELECT * FROM orders;";
    private static final String SQL_DELETE = "DELETE FROM orders WHERE id = ?;";
    private static final String SQL_CREATE = "INSERT INTO orders (registered, arrival, departure, correct_price," +
            " status, user_id, room_id) VALUES (?, ?, ?, ?, ?, ?, ?); ";
    private static final String SQL_UPDATE_STATUS = "UPDATE orders SET status=? WHERE id=?;";

    public OrderDao(Connection connection) {
        super(connection, new OrderMapper());
    }

    @Override
    public Order getById(Long id) {
        return getAbstractById(SQL_GET_ONE, id);
    }

    @Override
    public List<Order> findAll() {
        return findAbstractAll(SQL_GET_ALL);
    }

    public List<Order> findAll(int positionOnPage, int page) {
        return findAbstractAll(positionOnPage, page, SQL_GET_ALL);
    }

    @Override
    public boolean delete(Long id) {
        return deleteAbstract(SQL_DELETE, id);
    }

    @Override
    public Order update(Order room) {
        return null;
    }

    @Override
    public Order create(Order order) {
        return createAbstract(order, SQL_CREATE);
    }

    public boolean updateStatus(String s, long id) {
        return updateStringAbstract(s, id, SQL_UPDATE_STATUS);
    }

}
