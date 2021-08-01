package org.aleks4ay.hotel.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public final class Utils {

    private static final Logger log = LoggerFactory.getLogger(Utils.class);

    public static Connection getConnection() {
        Properties props = new Properties();
        String filePropertiesName = "database.properties";
        try (InputStream in = Utils.class.getClassLoader().getResourceAsStream(filePropertiesName)) {
            props.load(in);
        }
        catch (IOException e) {
            log.error("IOException during read {} from {}.", new File(filePropertiesName).getAbsolutePath(), Utils.class, e.toString());
            e.printStackTrace();
        }

        String url = props.getProperty("database.url");
        String username = props.getProperty("database.username");
        String password = props.getProperty("database.password");
        String driver = props.getProperty("database.driver");
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            log.debug("SQLException during get connection to url = {} from {}.", url, Utils.class, e);
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                log.debug("SQLException during close connection from {}.", Utils.class, e);
                try {
                    throw new SQLException(e);
                } catch (SQLException e1) {
                    log.warn(e1.getMessage());
                }
            }
        }
    }

    public static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                log.debug("SQLException during close Statement from {}.", Utils.class, e);
                try {
                    throw new SQLException(e);
                } catch (SQLException e1) {
                    log.warn(e1.getMessage());
                }
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.debug("SQLException during close ResultSet from {}.", Utils.class, e);
                try {
                    throw new SQLException(e);
                } catch (SQLException e1) {
                    log.warn(e1.getMessage());
                }
            }
        }
    }

}
