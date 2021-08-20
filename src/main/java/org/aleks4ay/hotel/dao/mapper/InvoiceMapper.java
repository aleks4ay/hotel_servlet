package org.aleks4ay.hotel.dao.mapper;

import org.aleks4ay.hotel.model.*;

import java.sql.*;

public class InvoiceMapper implements ObjectMapper<Invoice> {
    @Override
    public Invoice extractFromResultSet(ResultSet rs) throws SQLException {
        Invoice invoice = new Invoice(rs.getLong("id"));
        invoice.setRegistered(rs.getTimestamp("registered").toLocalDateTime());
        invoice.setCost(rs.getLong("cost"));
        invoice.setStatus(Invoice.Status.valueOf(rs.getString("status")));
        invoice.setOrder(new Order(rs.getLong("order_id")));

        return invoice;
    }

    @Override
    public void insertToResultSet(PreparedStatement statement, Invoice invoice) throws SQLException {
        statement.setLong(1, invoice.getCost());
        statement.setTimestamp(2, Timestamp.valueOf(invoice.getRegistered()));
        statement.setString(3, invoice.getStatus().toString());
    }
}