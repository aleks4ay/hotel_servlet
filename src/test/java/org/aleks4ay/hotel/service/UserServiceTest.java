package org.aleks4ay.hotel.service;

import org.aleks4ay.hotel.dao.ConnectionPool;
import org.aleks4ay.hotel.dao.ConnectionPoolTest;
import org.aleks4ay.hotel.exception.AlreadyException;
import org.aleks4ay.hotel.model.Role;
import org.aleks4ay.hotel.model.User;

import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.junit.*;
import static org.junit.Assert.*;

public class UserServiceTest {
    private Connection conn;
    private User expectedUserOne;
    private final ConnectionPool connectionPool = new ConnectionPoolTest();
    private final UserService service = new UserService(connectionPool);

    @Before
    public void getUserOne() {
        expectedUserOne = new User(
                "login1", "name1", "surname1", "0B14D501A594442A01C6859541BCB3E8164D183D32937B851835442F69D5C94E", true, LocalDateTime.of(2021, 9, 10, 15, 54, 44), 10);
        expectedUserOne.setId(1L);
        expectedUserOne.setRole(Role.ROLE_USER);
    }

    @Before
    public void setUp() throws Exception {
        conn = connectionPool.getConnection();
        Statement statement = conn.createStatement();
        statement.execute("delete from usr where true;");
        statement.execute("ALTER sequence id_seq restart with 1;");
        statement.execute("insert into usr (login, name, surname, password, registered, active, bill) VALUES " +
                "('login1', 'name1', 'surname1', '0B14D501A594442A01C6859541BCB3E8164D183D32937B851835442F69D5C94E', " +
                "'2021-09-10T15:54:44.0', true, 10), " +
                "('login2', 'name2', 'surname2', '6CF615D5BCAAC778352A8F1F3360D23F02F34EC182E259897FD6CE485D7870D4', " +
                "'2021-09-10T15:54:44.0', true, 20), " +
                "('login3', 'name3', 'surname3', '5906AC361A137E2D286465CD6588EBB5AC3F5AE955001100BC41577C3D751764', " +
                "'2021-09-10T15:54:44.0', true, 30);");
        statement.execute("insert into user_roles (user_id, role) VALUES " +
                "(1, 'ROLE_USER'), (2, 'ROLE_USER'), (3, 'ROLE_ADMIN');");
    }

    @After
    public void tearDown() {
        connectionPool.closeConnection(conn);
    }

    @Test
    public void getById() {
        assertEquals(expectedUserOne, service.findById(1L));
//        assertNotNull(service.getById(1L));
    }

    @Test
    public void getByLogin() {
        assertNotNull(service.findByLogin("login2"));
    }

    @Test
    public void getByLoginAndPassword() {
        assertNotNull(service.findByLoginAndPassword("login2", "password2"));
    }

    @Test
    public void findAll() {
        List<User> users = service.findAll("id");
        assertEquals(3, users.size());
    }

    @Test
    public void update() {
        User userFromDb = service.findByLogin("login2");
        userFromDb.setBill(1000);
        service.update(userFromDb);
        User userAfterUpdate = service.findByLogin("login2");
        assertEquals(userFromDb, userAfterUpdate);
    }

    @Test
    public void getAllAsMap() {
        Map<Long, User> allAsMap = service.getAllAsMap("id");
        assertEquals(3, allAsMap.entrySet().size());
    }

    @Test
    public void save() {
        User user = new User("login5", "name5", "surname5",
                "password5", true, LocalDateTime.now(), 10);
        assertEquals(0L, user.getId());
        user = service.save(user);
        assertNotEquals(0L, user.getId());
        assertEquals(Role.ROLE_USER, service.findByLogin("login5").getRole());
    }

    @Test(expected = AlreadyException.class)
    public void saveIfLoginOccupied() {
        service.save(new User(0L, "login1", "name1", "surname1", "password1"));
    }

    @Test
    public void doPagination()  {

    }

}