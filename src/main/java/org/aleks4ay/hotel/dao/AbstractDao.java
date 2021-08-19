package org.aleks4ay.hotel.dao;

import org.aleks4ay.hotel.dao.mapper.ObjectMapper;
import org.aleks4ay.hotel.model.BaseEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

abstract class AbstractDao<K, T extends BaseEntity> {

    public abstract Optional<T> findById(K key);
    public abstract List<T> findAll();
    public abstract Optional<T> create(T t);

    ObjectMapper<T> objectMapper;
    Connection connection;

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

    Optional<T> getAbstractById(String sql, long id) {
        Optional<T> result = Optional.empty();
        try (PreparedStatement prepStatement = connection.prepareStatement(sql)){
            prepStatement.setLong(1, id);
            ResultSet rs = prepStatement.executeQuery();
            if (rs.next()) {
                result = Optional.of(objectMapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    boolean updateAbstract(String sql, T t) {
        int result = 0;
        try (PreparedStatement prepStatement = connection.prepareStatement(sql)) {
            objectMapper.insertToResultSet(prepStatement, t);
            result = prepStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result == 1;
    }

    Optional<T> createAbstract(T t, String sql) {
        Optional<T> result = Optional.empty();
        try (PreparedStatement prepStatement = connection.prepareStatement(sql, new String[]{"id"})) {
            objectMapper.insertToResultSet(prepStatement, t);
            prepStatement.executeUpdate();

            ResultSet rs = prepStatement.getGeneratedKeys();
            if (rs.next()) {
                t.setId(rs.getLong(1));
                result = Optional.of(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
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
