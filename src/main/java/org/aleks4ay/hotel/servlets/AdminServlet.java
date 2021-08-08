package org.aleks4ay.hotel.servlets;

import org.aleks4ay.hotel.model.Category;
import org.aleks4ay.hotel.model.Room;
import org.aleks4ay.hotel.model.User;
import org.aleks4ay.hotel.service.RoomService;
import org.aleks4ay.hotel.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

//@WebServlet(urlPatterns = {"/admin", "/admin/newRoom"})
public class AdminServlet extends HttpServlet {
    private UserService userService = new UserService();
    private RoomService roomService = new RoomService();
    private static int POSITION_ON_PAGE = 10;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        String page = req.getParameter("pg");
        if (action == null) {
            action = "user";
        }
        req.setAttribute("action", action);
        if (page == null) {
            page = "1";
        }
        req.setAttribute("pg", page);
        List<Category> categories = Arrays.asList(Category.values());
        req.setAttribute("categories", categories);
//        System.out.println("action = "+ action);
//        System.out.println("page = " + page);
        if (action.equals("user")) {
            List<User> users = userService.getAll(POSITION_ON_PAGE, Integer.parseInt(page));

            req.setAttribute("users", users);
        } else if (action.equals("room")) {
            List<Room> rooms = roomService.getAll();
            req.setAttribute("rooms", rooms);
        } else if (action.equals("order")) {

        }
        req.getRequestDispatcher("/WEB-INF/jsp/adminPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int number = Integer.parseInt(req.getParameter("number"));
        String description = req.getParameter("description");
        Double price = Double.parseDouble(req.getParameter("price"));
        int guests = Integer.parseInt(req.getParameter("guests"));
        String selected = req.getParameter("category");
        Category category = Category.valueOf(selected);

        Room room = new Room(0L, number, category, guests, description, price);
//        HttpSession session = req.getSession();
//        User remoteUser = (User)session.getAttribute("user");
//        System.out.println("remoteUser = " + remoteUser);
        roomService.create(room);

        resp.sendRedirect("/admin?action=room");
    }
}
