package org.aleks4ay.hotel.service;

import org.aleks4ay.hotel.model.Order;
import org.aleks4ay.hotel.run.RunDao;

import java.util.List;

public class OrderService {
//    private UserDao userDao;
    private RunDao runDao = new RunDao();

    public List<Order> getAll() {
        RunDao runDao = new RunDao();
        return runDao.getOrders();
    }
}
