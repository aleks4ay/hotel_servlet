package org.aleks4ay.hotel.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.beans.PropertyVetoException;
import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

public final class ConnectionPool {
    private static final Logger log = LogManager.getLogger(ConnectionPool.class);

    private static DataSource dataSource;
    private static final String DRIVER_NAME;
    private static final String URL;
    private static final String USER_NAME;
    private static final String PASSWORD;

    static {
        final ResourceBundle config = ResourceBundle
                .getBundle("database", Locale.ENGLISH);
        // TODO: 02.08.2021 LOGIONG
        DRIVER_NAME = config.getString("database.driver");
        URL = config.getString("database.url");
        USER_NAME = config.getString("database.username");
        PASSWORD = config.getString("database.password");
        dataSource = initDataSource();
//        log.error("IOException during read {} from {}.", new File(filePropertiesName).getAbsolutePath(), Utils.class, e.toString());
    }

    public static Connection getConnection() {
        try {
            Connection connection = dataSource.getConnection();
            log.debug("Was getting Connection from {}.", ConnectionPool.class);
            return connection;
        } catch (SQLException e) {
            log.error("SQLException during get Connection from resource {}. {}",
                    new File("/database.properties").getAbsolutePath(), e);
        }
        return null;
    }

    private static DataSource initDataSource() {
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
        cpds.setMaxPoolSize(5); //20
        return cpds;
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                log.debug("SQLException during close connection from {}. {}", ConnectionPool.class, e);
            }
        }
    }
}
