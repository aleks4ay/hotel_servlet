package org.aleks4ay.hotel.servlets;


import org.aleks4ay.hotel.model.Category;
import org.aleks4ay.hotel.model.Room;
import org.aleks4ay.hotel.service.RoomService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@WebServlet(urlPatterns = "/home")
public class HomeServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("language") == null) {
            session.setAttribute("language", "ru");
        }
        req.setCharacterEncoding("UTF-8");
        String page = req.getParameter("pg");
        List<Room> roomList = new RoomService().getAll();
        req.setAttribute("rooms", roomList);
        List<Category> categories = Arrays.asList(Category.values());
        req.setAttribute("categories", categories);
        if (page == null || page.equals("")) {
            req.setAttribute("pg", 1);
        } else {
            req.setAttribute("pg", Integer.parseInt(page));
        }
        req.getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(req, resp);
    }
}
