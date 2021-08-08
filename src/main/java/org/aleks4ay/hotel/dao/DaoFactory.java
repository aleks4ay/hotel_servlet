package org.aleks4ay.hotel.dao;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public abstract RoomDao createRoomDao();
    public abstract UserDao createUserDao();

    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            synchronized (DaoFactory.class) {
                if (daoFactory == null) {
                    DaoFactory temp = new JDBCDaoFactory();
                    daoFactory = temp;
                }
            }
        }
        return daoFactory;
    }
}
