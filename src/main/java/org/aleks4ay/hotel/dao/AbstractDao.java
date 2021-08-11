package org.aleks4ay.hotel.dao;

import org.aleks4ay.hotel.dao.mapper.ObjectMapper;
import org.aleks4ay.hotel.model.BaseEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

abstract class AbstractDao<K, T extends BaseEntity> {

    ObjectMapper<T> objectMapper;
    public abstract T getById(K key);
    public abstract List<T> findAll();
    public abstract boolean delete(K id);
    public abstract T update(T t);
    public abstract T create(T t);

    Connection connection = null;

    AbstractDao(Connection connection, ObjectMapper<T> objectMapper) {
        this.connection = connection;
        this.objectMapper = objectMapper;
    }

    List<T> findAbstractAll(String sql) {
        List<T> entities = new ArrayList<>();
        try (Statement st = connection.createStatement()){
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                T t = objectMapper.extractFromResultSet(rs);
                entities.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }

    T getAbstractById(String sql, long id) {
        try (PreparedStatement prepStatement = connection.prepareStatement(sql)){
            prepStatement.setLong(1, id);
            ResultSet rs = prepStatement.executeQuery();
            if (rs.next()) {
                return objectMapper.extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteAbstract(String sql, long id) {
        boolean result = false;
        try (PreparedStatement prepStatement = connection.prepareStatement(sql)) {
            prepStatement.setLong(1, id);
            result = (1 == prepStatement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

/*    List<T> findAbstractAll(int positionOnPage, int page, String sql) {
        int startPosition = positionOnPage * (page - 1);
        List<T> entities = findAbstractAll(sql);
        List<T> roomsAfterFilter = new ArrayList<>();

        if (entities.size() > startPosition) {
            for (int i = startPosition; i < startPosition + positionOnPage; i++) {
                if (i >= entities.size()) {
                    break;
                }
                roomsAfterFilter.add(entities.get(i));
            }
            return roomsAfterFilter;
        }
        return new ArrayList<>();
    }*/

    boolean updateAbstract(String sqlCreate, T t) {
        int result = 0;
        try (PreparedStatement prepStatement = connection.prepareStatement(sqlCreate)) {
            objectMapper.insertToResultSet(prepStatement, t);
            result = prepStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result == 1;
    }

    T createAbstract(T t, String sql) {
        try (PreparedStatement prepStatement = connection.prepareStatement(sql, new String[]{"id"})) {
            objectMapper.insertToResultSet(prepStatement, t);
            prepStatement.executeUpdate();

            ResultSet rs = prepStatement.getGeneratedKeys();
            if (rs.next()) {
                t.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return t;
    }

    boolean updateStringAbstract(String s, long id, String sql) {
        try (PreparedStatement prepStatement = connection.prepareStatement(sql)) {
            prepStatement.setString(1, s);
            prepStatement.setLong(2, id);
            return prepStatement.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
