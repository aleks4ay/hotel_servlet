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
import java.util.List;

class AdminCommand implements Command {
    private final static int POSITION_ON_PAGE = 8;

    @Override
    public String execute(HttpServletRequest request) {
        if(request.getSession().getAttribute("user") == null) {
            return "/WEB-INF/index.jsp";
        }
        String action = request.getParameter("action") == null ? "user" : request.getParameter("action");
        request.setAttribute("itemOnPage", POSITION_ON_PAGE);

        if (action.equalsIgnoreCase("newRoom")){
            int number = Integer.parseInt(request.getParameter("number"));
            String description = request.getParameter("description");
            long price = Long.parseLong(request.getParameter("price"));
            int guests = Integer.parseInt(request.getParameter("guests"));
            Category category = Category.valueOf(request.getParameter("category"));
            String imgName = request.getParameter("imgName");

            new RoomService(new ConnectionPool()).create(new Room(number, category, guests, description, price, imgName));
            return "redirect:/admin?action=room";
        }

        request.setAttribute("action", action);

        if (action.equalsIgnoreCase("user")) {
            UserService userService = new UserService(new ConnectionPool());
            List<User> userList = userService.findAll();
            userList = userService.doPagination(POSITION_ON_PAGE, (int) request.getAttribute("pg"), userList);
            request.setAttribute("users", userList);

        } else if (action.equalsIgnoreCase("room")){
            RoomService roomService = new RoomService(new ConnectionPool());
            List<Room> roomList = roomService.getAll();
            roomList = roomService.doPagination(POSITION_ON_PAGE, (int) request.getAttribute("pg"), roomList);
            request.setAttribute("rooms", roomList);
            request.setAttribute("categories", Category.values());

        } else if (action.equalsIgnoreCase("order")){
            List<Order> orderList = new OrderService(new ConnectionPool()).getAll();
            request.setAttribute("orders", orderList);
        }
        return "WEB-INF/jsp/adminPage.jsp";
    }
}