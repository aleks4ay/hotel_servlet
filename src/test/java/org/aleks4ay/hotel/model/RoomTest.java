package org.aleks4ay.hotel.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
//import org.junit.*;
import static org.junit.Assert.*;

public class RoomTest {
    private Room room1, room2, room3;
    private List<Room> rooms = new LinkedList<>();

    @BeforeEach
    public void setUp() {
        room1 = new Room(101, Category.STANDARD, 2, "Номер с видом на море", 1_600, "101.jpg");
        room2 = new Room(106, Category.SUPERIOR, 3, "Номер с wi-fi и видом на море", 2_800, "106.jpg");
        room3 = new Room(501, Category.DELUXE, 5, "Люкс с бассейном", 100_000, "501.jpg");

        rooms.addAll(Arrays.asList(room1, room2, room3));
    }

  /*  @Test
    public void testSetStatus() throws Exception {
        boolean result = room1.setStatus(LocalDate.of(2021,8,1), RoomStatus.BOOKED);
        assertEquals(true, result);
    }

    @Test
    public void testSetStatusEmptyWhenNewDate() throws Exception {
        LocalDate newDate = LocalDate.of(2021, 8, 5);
        room1.setStatus(newDate, RoomStatus.EMPTY);
        Object actual = room1.getStatuses().get(newDate);
        assertNull(actual);
    }

    @Test
    public void testSetStatusEmptyWhenOldDate() throws Exception {
        LocalDate newDate = LocalDate.of(2021, 8, 2);
        room1.setStatus(newDate, RoomStatus.EMPTY);
        Object actual = room1.getStatuses().get(newDate);
        assertNull(actual);
    }

    @Test
    public void testSetStatusWhenOldDate() throws Exception {
        LocalDate newDate = LocalDate.of(2021, 8, 2);
        RoomStatus expected = RoomStatus.DISABLED;
        room1.setStatus(newDate, RoomStatus.DISABLED);
        RoomStatus actual = room1.getStatuses().get(newDate);
        assertEquals(actual, expected);
    }

    @Test
    public void testIsEmpty() {
        LocalDate newDate = LocalDate.of(2021, 8, 5);
        assertTrue(room1.isEmpty(newDate));
    }

    @Test
    public void testIsEmptyWhenAccupied() {
        LocalDate newDate = LocalDate.of(2021, 8, 2);
        assertFalse(room1.isEmpty(newDate));
    }*/

    /*@Test
    public void testIsEmptyRange() {
        LocalDate start = LocalDate.of(2021, 8, 3);
        LocalDate end = LocalDate.of(2021, 8, 7);
        assertTrue(room1.isEmpty(start, end));
    }

    @Test
    public void testIsEmptyRangeWhenAccupied() {
        LocalDate start = LocalDate.of(2021, 8, 2);
        LocalDate end = LocalDate.of(2021, 8, 7);
        assertFalse(room1.isEmpty(start, end));
    }

    @Test
    public void testToString() throws Exception {
        String expected = "Room{id=1, number=101, category=Category['STANDARD'], guests=2, description='Номер с видом на море', " +
                "price=1600.0, statuses={2021-07-31=BOOKED, 2021-08-02=OCCUPIED, 2021-08-01=DISABLED}}";
        String actual = room1.toString();
        assertEquals(expected, actual);
    }*/
}
