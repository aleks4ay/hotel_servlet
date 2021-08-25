package org.aleks4ay.hotel.command;

import org.aleks4ay.hotel.dao.ConnectionPool;
import org.aleks4ay.hotel.exception.AlreadyException;
import org.aleks4ay.hotel.model.*;
import org.aleks4ay.hotel.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

class AdminCommand implements Command {
    private static final Logger log = LogManager.getLogger(AdminCommand.class);
    private final static int POSITION_ON_PAGE = 6;
    private final RoomService roomService = new RoomService(new ConnectionPool());
    private final UserService userService = new UserService(new ConnectionPool());
    private final OrderService orderService = new OrderService(new ConnectionPool());

    @Override
    public String execute(HttpServletRequest request) {
        if(request.getSession().getAttribute("user") == null) {
            return "/WEB-INF/jsp/index.jsp";
        }
        String action = request.getParameter("action") == null ? "user" : request.getParameter("action");
        request.setAttribute("action", action);
        request.setAttribute("itemOnPage", POSITION_ON_PAGE);

        if (action.equalsIgnoreCase("newRoom")){
            return newRoom(request);
        }
        if (action.equalsIgnoreCase("saveChangedRoom")){
            return saveChangedRoom(request);
        }
        if (action.equalsIgnoreCase("changeRoom")) {
            return changeRoom(request);
        }

        if (action.equalsIgnoreCase("user")) {
            getUsers(request);
        } else if (action.equalsIgnoreCase("room")){
            getRooms(request);
        } else if (action.equalsIgnoreCase("order")){
            getOrders(request);
        }
        return "WEB-INF/jsp/adminPage.jsp";
    }

    private String changeRoom(HttpServletRequest request) {
        request.setAttribute("categories", Category.values());
        request.setAttribute("room", roomService.findById(Long.parseLong(request.getParameter("id"))));
        return "WEB-INF/jsp/adminPage.jsp";
    }

    private String saveChangedRoom(HttpServletRequest request) {
        roomService.update(Utils.getNewRoomFromRequest(request));
        log.info("Was update Room #{}.", request.getParameter("number"));
        return "redirect:/admin?action=room&pg=" + request.getAttribute("pg");
    }

    private String newRoom(HttpServletRequest request) {
        Room room = Utils.getNewRoomFromRequest(request);
        try {
            room = roomService.create(room);
            log.info("Was save new Room '{}'", room);
        } catch (AlreadyException e) {
            roomService.addOldValues(request, room);
            request.setAttribute("action", "room");
            getRooms(request);
            return "WEB-INF/jsp/adminPage.jsp";
        }
        return "redirect:/admin?action=room&pg=" + request.getAttribute("pg");
    }

    private void getUsers(HttpServletRequest request) {
        List<User> userList = userService.findAll("login");
        userList = userService.doPagination(POSITION_ON_PAGE, (int) request.getAttribute("pg"), userList);
        request.setAttribute("users", userList);
    }

    private void getRooms(HttpServletRequest request) {
        Utils.initSortMethod(request);
        List<Room> roomList = roomService.findAll((String) request.getAttribute("sortMethod"), new ArrayList<>());
        roomList = roomService.doPagination(POSITION_ON_PAGE, (int) request.getAttribute("pg"), roomList);
        request.setAttribute("rooms", roomList);
        request.setAttribute("categories", Category.values());
    }

    private void getOrders(HttpServletRequest request) {
        List<Order> orderList = orderService.findAll("id");
        orderList = orderService.doPagination(POSITION_ON_PAGE, (int) request.getAttribute("pg"), orderList);
        request.setAttribute("orders", orderList);
    }
}