package org.aleks4ay.hotel.service;

import org.aleks4ay.hotel.dao.ConnectionPool;
import org.aleks4ay.hotel.dao.InvoiceDao;
import org.aleks4ay.hotel.exception.CannotSaveException;
import org.aleks4ay.hotel.exception.NotFoundException;
import org.aleks4ay.hotel.model.Invoice;
import org.aleks4ay.hotel.model.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InvoiceService {
    private static final Logger log = LogManager.getLogger(InvoiceService.class);

    private final ConnectionPool connectionPool;

    public InvoiceService(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public Invoice findById(long id) {
        Connection conn = connectionPool.getConnection();
        Invoice invoice = new InvoiceDao(conn).findById(id).orElseThrow(
                () -> new NotFoundException("Invoice with id=" + id + "not found"));
        connectionPool.closeConnection(conn);
        return invoice;
    }

    public Optional<Invoice> findByOrderId(long orderId) {
        Connection conn = connectionPool.getConnection();
        Optional<Invoice> invoiceOptional = new InvoiceDao(conn).findByOrderId(orderId);
        connectionPool.closeConnection(conn);
        return invoiceOptional;
    }

    public List<Invoice> findAll() {
        Connection conn = connectionPool.getConnection();
        List<Invoice> invoices = new InvoiceDao(conn).findAll("id");
        connectionPool.closeConnection(conn);
        return invoices;
    }

    Map<Long, Invoice> findAllAsMap() {
        return findAll().stream()
                .collect(Collectors.toMap(i -> i.getOrder().getId(), i -> i));
    }

    public Invoice create(Invoice invoice) {
        Connection conn = connectionPool.getConnection();
        InvoiceDao dao = new InvoiceDao(conn);
        Invoice invoiceAfterSave = dao.create(invoice).orElseThrow(
                () -> new CannotSaveException("Invoice with id=" + invoice + "cannot to save"));
        dao.saveOrderLink(invoice);
        connectionPool.closeConnection(conn);
        return invoiceAfterSave;
    }

    public boolean updateStatus(Invoice invoice) {
        Connection conn = connectionPool.getConnection();
        boolean result = new InvoiceDao(conn).updateStatus(invoice);
        connectionPool.closeConnection(conn);
        return result;
    }
}
