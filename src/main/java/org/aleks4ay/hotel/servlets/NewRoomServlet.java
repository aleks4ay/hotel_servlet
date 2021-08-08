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
import java.util.Arrays;
import java.util.List;

//@WebServlet(urlPatterns = "/admin/newRoom")
public class NewRoomServlet extends HttpServlet {
//    private UserService userService = new UserService();
    private RoomService roomService = new RoomService();
//    private static int POSITION_ON_PAGE = 10;

 /*   @Override
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
//        System.out.println("action = "+ action);
//        System.out.println("page = " + page);
        if (action.equals("user")) {
            List<User> users = userService.getAll(POSITION_ON_PAGE, Integer.parseInt(page));

            req.setAttribute("users", users);
        } else if (action.equals("room")) {
            List<Room> rooms = roomService.getAll();
            req.setAttribute("rooms", rooms);
            List<Category> categories = Arrays.asList(Category.values());
            req.setAttribute("categories", categories);
        } else if (action.equals("order")) {

        }
        req.getRequestDispatcher("/WEB-INF/jsp/adminPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String login = req.getParameter("login");
        String pass = req.getParameter("log_pass");
//        System.out.println("login = " + login);
//        System.out.println("pass = " + pass);
        boolean isValidLogin = userService.checkLogin(login);
        if (!isValidLogin) {
            req.setAttribute("wrongLogin", "Login is wrong!");
            req.setAttribute("oldLogin", login);
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
        }

        boolean isValidPassword = userService.checkPassword(login, pass);
        if (!isValidPassword) {
            req.setAttribute("wrongPass", "Password is wrong!");
            req.setAttribute("oldLogin", login);
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
        } else {
            User user = userService.getByLogin(login);
            HttpSession session = req.getSession();
            session.setAttribute("user", user);

            String url = (String) session.getAttribute("backUrl");
            if (url == null) {
                url = "/home";
            }
            resp.sendRedirect(url);
        }
    }*/
}
