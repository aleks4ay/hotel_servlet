package org.aleks4ay.hotel.dao;

public final class ConnectionPoolTest extends ConnectionPool {
    static {
        dataSource = initDataSource(getUrlTest());
    }
}
