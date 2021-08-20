package org.aleks4ay.hotel.service;

import org.aleks4ay.hotel.dao.ConnectionPool;
import org.aleks4ay.hotel.dao.InvoiceDao;
import org.aleks4ay.hotel.exception.CannotSaveException;
import org.aleks4ay.hotel.exception.NotFoundException;
import org.aleks4ay.hotel.model.Invoice;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;

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

    public Invoice findByOrderId(long orderId) {
        Connection conn = connectionPool.getConnection();
        Invoice invoice = new InvoiceDao(conn).findByOrderId(orderId).orElseThrow(
                () -> new NotFoundException("Invoice with id=" + orderId + "not found"));
        connectionPool.closeConnection(conn);
        return invoice;
    }

    public List<Invoice> findAll(String sortMethod) {
        Connection conn = connectionPool.getConnection();
        List<Invoice> invoices = new InvoiceDao(conn).findAll(sortMethod);
        connectionPool.closeConnection(conn);
        return invoices;
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
}
