package org.aleks4ay.hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao<T, K> {


    public abstract <T> T getById(K key);
    public abstract List<T> getAll();
    public abstract boolean delete(K id);
    public abstract T update(T t);
    public abstract boolean create(T t);
    public abstract <T>T readEntity(ResultSet rs) throws SQLException;
    public abstract void fillEntityStatement(PreparedStatement statement, T t) throws SQLException;

    public Connection connection = null;

    protected AbstractDao(Connection connection) {
        this.connection = connection;
    }

    public List<T> getAbstractAll(String sql) {
        List<T> result = new ArrayList<T>();
        ResultSet rs = null;
        PreparedStatement prepStatement = null;
        try {
            prepStatement = connection.prepareStatement(sql);
            rs = prepStatement.executeQuery();
            while (rs.next()) {
                result.add(readEntity(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Utils.closeResultSet(rs);
            Utils.closeStatement(prepStatement);
        }
        return result;
    }

    public <T>T getAbstractById(String sql, long id) {
        ResultSet rs = null;
        PreparedStatement prepStatement = null;
        try {
            prepStatement = connection.prepareStatement(sql);
            prepStatement.setLong(1, id);
            rs = prepStatement.executeQuery();
            if (rs.next()) {
                return readEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Utils.closeResultSet(rs);
            Utils.closeStatement(prepStatement);
        }
        return null;
    }

    public <T>T getAbstractByName(String sql, String name) {
        ResultSet rs = null;
        PreparedStatement prepStatement = null;
        try {
            prepStatement = connection.prepareStatement(sql);
            prepStatement.setString(1, name);
            rs = prepStatement.executeQuery();
            if (rs.next()) {
                return readEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Utils.closeResultSet(rs);
            Utils.closeStatement(prepStatement);
        }
        return null;
    }

    public boolean deleteAbstract(String sql, long id) {
        boolean result = false;
        PreparedStatement prepStatement = null;
        try {
            prepStatement = connection.prepareStatement(sql);
            prepStatement.setLong(1, id);
            result = (1 == prepStatement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Utils.closeStatement(prepStatement);
        }
        return result;
    }

    public boolean createAbstract(String sqlCreate, T t) {
        boolean result = false;
        PreparedStatement prepStatement = null;
        try {
            prepStatement = connection.prepareStatement(sqlCreate);
            fillEntityStatement(prepStatement, t);
            result = (1 == prepStatement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Utils.closeStatement(prepStatement);
        }
        return result;
    }
}
