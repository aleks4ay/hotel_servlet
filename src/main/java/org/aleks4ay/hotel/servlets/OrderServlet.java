package org.aleks4ay.hotel.servlets;

import org.aleks4ay.hotel.model.Order;
import org.aleks4ay.hotel.service.OrderService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@WebServlet(urlPatterns = "/order")
public class OrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String id = req.getParameter("id");
        String page = req.getParameter("pg");
        String oldId = (String) session.getAttribute("id");
        String action = req.getParameter("action");
        if (action.equals("select")) {
            if (oldId != null) {
                id = oldId + ", " + id;
            }
            session.setAttribute("id", id);
            resp.sendRedirect("/home?pg=" + page);
        } else if (action.equals("booking")) {
            Order order = new OrderService().getAll().get(0);
            req.setAttribute("order", order);
            req.getRequestDispatcher("/WEB-INF/jsp/booking.jsp").forward(req, resp);
        }
    }
}
