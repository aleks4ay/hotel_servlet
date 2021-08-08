package org.aleks4ay.hotel.dao;

import javax.sql.DataSource;

public final class ConnectionPoolHolder {

    private static volatile DataSource dataSource;

    public static DataSource getDataSource() {
/*        if (dataSource == null) {
            synchronized (ConnectionPoolHolder.class) {
                if (dataSource == null) {
                    BasicDataSource ds = new BasicDataSource();
                    ds.setUrl();
                    ds.setUserName();
                    ds.setPassword();
                    ds.setMinIdle(5);
                    ds.setMaxIdle(10);
                    ds.setMaxOpenPreparedStatements(100);
                    dataSource = ds;
                }
            }
        }*/
        return dataSource;
    }
}
