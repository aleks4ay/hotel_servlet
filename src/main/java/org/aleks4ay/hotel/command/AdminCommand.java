package org.aleks4ay.hotel.command;

import org.aleks4ay.hotel.dao.ConnectionPool;
import org.aleks4ay.hotel.model.Category;
import org.aleks4ay.hotel.model.Order;
import org.aleks4ay.hotel.model.Room;
import org.aleks4ay.hotel.model.User;
import org.aleks4ay.hotel.service.OrderService;
import org.aleks4ay.hotel.service.RoomService;
import org.aleks4ay.hotel.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

class AdminCommand implements Command {
    private final static int POSITION_ON_PAGE = 4;
    private final RoomService roomService = new RoomService(new ConnectionPool());
    private final UserService userService = new UserService(new ConnectionPool());
    private final OrderService orderService = new OrderService(new ConnectionPool());

    @Override
    public String execute(HttpServletRequest request) {
        if(request.getSession().getAttribute("user") == null) {
            return "/WEB-INF/jsp/index.jsp";
        }
        String action = request.getParameter("action") == null ? "user" : request.getParameter("action");
        request.setAttribute("itemOnPage", POSITION_ON_PAGE);

        if (action.equalsIgnoreCase("newRoom") || action.equalsIgnoreCase("saveChangedRoom")){
            if (action.equalsIgnoreCase("newRoom")) {
                roomService.create(Utils.getNewRoomFromRequest(request, true));
            } else {
                roomService.update(Utils.getNewRoomFromRequest(request, false));
            }
            return "redirect:/admin?action=room&pg=" + request.getAttribute("pg");
        }

        request.setAttribute("action", action);

        if (action.equalsIgnoreCase("changeRoom")) {
            request.setAttribute("categories", Category.values());
            request.setAttribute("room", roomService.findById(Long.parseLong(request.getParameter("id"))));
            return "WEB-INF/jsp/adminPage.jsp";
        }

        if (action.equalsIgnoreCase("user")) {
            List<User> userList = userService.findAll("login");
            userList = userService.doPagination(POSITION_ON_PAGE, (int) request.getAttribute("pg"), userList);
            request.setAttribute("users", userList);

        } else if (action.equalsIgnoreCase("room")){
            Utils.initSortMethod(request);
            List<Room> roomList = roomService.findAll((String) request.getAttribute("sortMethod"), new ArrayList<>());
            roomList = roomService.doPagination(POSITION_ON_PAGE, (int) request.getAttribute("pg"), roomList);

            request.setAttribute("rooms", roomList);
            request.setAttribute("categories", Category.values());

        } else if (action.equalsIgnoreCase("order")){
            List<Order> orderList = orderService.findAll("id");
            orderList = orderService.doPagination(POSITION_ON_PAGE, (int) request.getAttribute("pg"), orderList);
            request.setAttribute("orders", orderList);
        }
        return "WEB-INF/jsp/adminPage.jsp";
    }
}