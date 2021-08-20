package org.aleks4ay.hotel;

import org.aleks4ay.hotel.dao.*;
import org.aleks4ay.hotel.model.RoomTest;
import org.aleks4ay.hotel.service.*;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@Suite.SuiteClasses(
        {
                OrderDaoTest.class,
                RoomDaoTest.class,
                UserDaoTest.class,
//                RoomTest.class,
                OrderServiceTest.class,
                UserServiceTest.class
        })
@RunWith(Suite.class)
public class TestSuiteDemo {
}
