package org.aleks4ay.hotel.dao;

import java.util.List;

public interface GenericDao<T> extends AutoCloseable {
    T create(T entity);
    T findById(long id);
    List<T> findAll();
    boolean delete(long id);
    boolean update(T entity);
    void close();
//    public abstract <T>T readEntity(ResultSet rs) throws SQLException;
//    public abstract void fillEntityStatement(PreparedStatement statement, T t) throws SQLException;
}
