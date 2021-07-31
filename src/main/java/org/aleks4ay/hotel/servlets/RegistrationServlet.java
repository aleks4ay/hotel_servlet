package org.aleks4ay.hotel.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/registration")
public class RegistrationServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        req.getRequestDispatcher("/WEB-INF/jsp/newUser.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String checkLogin = req.getParameter("checkLogin");
        if (checkLogin == null) {
            System.out.println("checkLogin is null");
        } else if (checkLogin.equalsIgnoreCase("on")){
            System.out.println("checkLogin is: " + checkLogin);
        }

        String login = req.getParameter("loginNew");
        String pass = req.getParameter("log_pass");
        System.out.println("login = " + login);
        System.out.println("pass = " + pass);
        resp.sendRedirect("/login");
        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
    }
}
