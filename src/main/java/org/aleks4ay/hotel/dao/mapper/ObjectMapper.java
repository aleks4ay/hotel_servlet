package org.aleks4ay.hotel.dao.mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public interface ObjectMapper<T> {

    T extractFromResultSet(ResultSet rs) throws SQLException;
    void insertToResultSet(PreparedStatement statement, T entity) throws SQLException;

    T makeUnique(Map<Long, T> cache, T entity);
}
