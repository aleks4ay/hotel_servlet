package org.aleks4ay.hotel.dao;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {

    private DataSource dataSource = ConnectionPoolHolder.getDataSource();

    @Override
    public RoomDao createRoomDao() {
        return new RoomDao(getConnection());
    }

    @Override
    public UserDao createUserDao() {
        return new UserDao(getConnection());
    }

    private Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
