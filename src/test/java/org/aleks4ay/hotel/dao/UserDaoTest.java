package org.aleks4ay.hotel.dao;

import org.aleks4ay.hotel.exception.NotEmptyRoomException;
import org.aleks4ay.hotel.model.Role;
import org.aleks4ay.hotel.model.User;

import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.*;
import static org.junit.Assert.*;

public class UserDaoTest {
    private static User expectedUserOne;
    private final ConnectionPool connectionPool = new ConnectionPoolTest();
    private UserDao dao;
    private Connection conn;

    @BeforeClass
    public static void getUserOne() {
        expectedUserOne = new User(
                "login1", "name1", "surname1", "password1", true, LocalDateTime.of(2021, 9, 10, 15, 54, 44), 10);
        expectedUserOne.setId(1L);
        expectedUserOne.setRole(Role.ROLE_USER);
    }

    @Before
    public void setUp() throws Exception {
        conn = connectionPool.getConnection();
        dao = new UserDao(conn);
        Statement statement = conn.createStatement();
        statement.execute("delete from usr where true;");
        statement.execute("ALTER sequence id_seq restart with 1;");
        statement.execute("insert into usr (login, name, surname, password, active, registered, bill) VALUES " +
                "('login1', 'name1', 'surname1', 'password1', true, '2021-09-10T15:54:44.0', 10), " +
                "('login2', 'name2', 'surname2', 'password2', true, '2021-09-10T15:54:44.0', 20);");
        statement.execute("insert into user_roles (user_id, role) VALUES (1, 'ROLE_USER'), (2, 'ROLE_ADMIN');");
    }

    @After
    public void tearDown() throws Exception {
        connectionPool.closeConnection(conn);
    }

    @Test
    public void findById() {
        assertEquals(expectedUserOne, dao.findById(1L).get());
    }

    @Test
    public void testGetByLogin() {
        assertEquals(expectedUserOne, dao.findByLogin("login1").get());
    }

    @Test
    public void testGetByLoginAndPassword() {
        assertEquals(expectedUserOne, dao.findByLoginAndPassword("login1", "password1").get());
    }

    @Test
    public void findAll() {
        final List<User> users = dao.findAll("id");
        assertEquals(2, users.size());
    }

    @Test
    public void update() {
        User userFromDb = dao.findByLogin("login1").orElseThrow(() -> new NotEmptyRoomException("user not found"));
        userFromDb.setBill(1000);
        dao.update(userFromDb);
        User userAfterUpdate = dao.findByLogin("login1").orElseThrow(() -> new NotEmptyRoomException("user not found"));
        assertEquals(userFromDb, userAfterUpdate);
    }

    @Test
    public void updateRole() {
        User userFromDb = dao.findByLogin("login1").orElseThrow(() -> new NotEmptyRoomException("user not found"));
        userFromDb.setRole(Role.ROLE_MANAGER);
        dao.updateRole(userFromDb);
        User userAfterUpdate = dao.findByLogin("login1").orElseThrow(() -> new NotEmptyRoomException("user not found"));
        assertEquals(Role.ROLE_MANAGER, userAfterUpdate.getRole());
    }

    @Test
    public void createRole() {
        User userFromDb = dao.findByLogin("login1").orElseThrow(() -> new NotEmptyRoomException("user not found"));
        userFromDb.setRole(Role.ROLE_MANAGER);
        assertTrue(dao.createRole(userFromDb));
    }

    @Test
    public void create() {
        User user = new User("login5", "name5", "surname5",
                "password5", true, LocalDateTime.now(), 10);
        assertEquals(0L, user.getId());
        Optional<User> userOptional = dao.create(user);
        assertNotEquals(0L, userOptional.get().getId());
    }
}