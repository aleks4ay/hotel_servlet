package org.aleks4ay.hotel.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public final class ConnectionPool {

    private static DataSource dataSource;
    private static final String DRIVER_NAME;
    private static final String URL;
    private static final String USER_NAME;
    private static final String PASSWORD;

    static {
        final ResourceBundle config = ResourceBundle
                .getBundle("database", Locale.ENGLISH);
        DRIVER_NAME = config.getString("database.driver");
        URL = config.getString("database.url");
        USER_NAME = config.getString("database.username");
        PASSWORD = config.getString("database.password");
        dataSource = setupDataSource();
    }

    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static DataSource setupDataSource() {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        try {
            cpds.setDriverClass(DRIVER_NAME);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        cpds.setJdbcUrl(URL);
        cpds.setUser(USER_NAME);
        cpds.setPassword(PASSWORD);
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(20);
        return cpds;
    }

/*    public static void main(String[] args) throws SQLException {
        for (int i = 0; i < 10; i++) {
            long start = System.currentTimeMillis();
            new ConnectionPool().doTestWithPool();
            long end = System.currentTimeMillis();
            System.out.println("With pool. time = " + (end - start));
        }
        for (int i = 0; i < 10; i++) {
            long start = System.currentTimeMillis();
            new ConnectionPool().doTestNoPool();
            long end = System.currentTimeMillis();
            System.out.println("No pool. time = " + (end - start));
        }
    }*/

/*    private void doTestNoPool() {
        Connection conn = Utils.getConnection();
        UserDao userDao = new UserDao(conn);
        User user = userDao.create(new User(null, "ert12", "rt1", "rt1", "rt1"));
        Utils.closeConnection(conn);
        Connection conn2 = Utils.getConnection();
        UserDao userDao2 = new UserDao(conn2);
        userDao2.delete(user.getId());
        Utils.closeConnection(conn2);
    }

    private void doTestWithPool() {
        Connection conn = ConnectionPool.getConnection();
        UserDao userDao = new UserDao(conn);
        User user = userDao.create(new User(null, "ert6", "rt", "rt", "rt"));
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Connection conn2 = ConnectionPool.getConnection();
        UserDao userDao2 = new UserDao(conn2);
        userDao2.delete(user.getId());
        Utils.closeConnection(conn2);
    }*/
}
