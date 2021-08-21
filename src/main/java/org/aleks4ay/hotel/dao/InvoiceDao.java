package org.aleks4ay.hotel.dao;

import org.aleks4ay.hotel.dao.mapper.InvoiceMapper;
import org.aleks4ay.hotel.model.Invoice;
import org.aleks4ay.hotel.model.Order;
import org.aleks4ay.hotel.model.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class InvoiceDao extends AbstractDao<Long, Invoice>{
    private static final Logger log = LogManager.getLogger(InvoiceDao.class);

    public InvoiceDao(Connection connection) {
        super(connection, new InvoiceMapper());
    }

    @Override
    public Optional<Invoice> findById(Long id) {
        return getAbstractById("SELECT i.*, x.order_id FROM invoice i join order_invoice x on i.id = x.invoice_id " +
                "AND i.id = ?;", id);
    }

    @Override
    public List<Invoice> findAll(String sortMethod) {
        return findAbstractAll("SELECT i.*, x.order_id FROM invoice i join order_invoice x on i.id = x.invoice_id order by "
                + sortMethod + ";");
    }

    public Optional<Invoice> findByOrderId(long orderId) {
        return getAbstractById("SELECT i.*, x.order_id FROM invoice i join order_invoice x on " +
                "i.id = x.invoice_id AND x.order_id = ?;", orderId);
    }

    @Override
    public Optional<Invoice> create(Invoice invoice) {
        String sql = "INSERT INTO invoice (cost, registered, status) VALUES (?, ?, ?); ";
        return createAbstract(invoice, sql);
    }

    public void saveOrderLink(Invoice invoice) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO order_invoice (order_id, invoice_id) " +
                        "VALUES (?, ?);") ) {
            statement.setLong(1, invoice.getOrder().getId());
            statement.setLong(2, invoice.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.warn("Exception during create order-invoice link '{}'. {}", invoice, e);
        }
    }

    public boolean updateStatus(Invoice i) {
        return updateStringAbstract(i.getStatus().toString(), i.getId(), "update invoice set status = ? where id = ?;");
    }
}
