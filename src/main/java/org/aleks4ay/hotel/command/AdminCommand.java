package org.aleks4ay.hotel.command;

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
    private final static int POSITION_ON_PAGE = 10;

    @Override
    public String execute(HttpServletRequest request) {
        if(request.getSession().getAttribute("user") == null) {
            return "/WEB-INF/index.jsp";
        }
        String action = request.getParameter("action");
        String page = request.getParameter("pg");
        if (page == null) {
            page = "1";
        }
        request.setAttribute("pg", page);
        request.setAttribute("itemOnPage", POSITION_ON_PAGE);

        if (action == null) {
            action = "user";
        }

        if (action.equalsIgnoreCase("newRoom")){
            int number = Integer.parseInt(request.getParameter("number"));
            String description = request.getParameter("description");
            Double price = Double.parseDouble(request.getParameter("price"));
            int guests = Integer.parseInt(request.getParameter("guests"));
            Category category = Category.valueOf(request.getParameter("category"));

            new RoomService().create(new Room(0L, number, category, guests, description, price));
            return "redirect:/admin?action=room";
        }

        request.setAttribute("action", action);

        if (action.equalsIgnoreCase("user")) {
            List<User> userList = new UserService().getAll(POSITION_ON_PAGE, Integer.parseInt(page));
            request.setAttribute("users", userList);

        } else if (action.equalsIgnoreCase("room")){
            List<Room> roomList = new RoomService().getAll(POSITION_ON_PAGE, Integer.parseInt(page));
            request.setAttribute("rooms", roomList);
            request.setAttribute("categories", Category.values());

        } else if (action.equalsIgnoreCase("order")){
            List<Order> orderList = new OrderService().getAll();
            request.setAttribute("orders", orderList);

        }
        return "WEB-INF/jsp/adminPage.jsp";
    }
}