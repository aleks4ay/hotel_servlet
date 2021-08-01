package org.aleks4ay.hotel.servlets;

import org.aleks4ay.hotel.model.User;
import org.aleks4ay.hotel.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URL;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String url = new URL(req.getHeader("Referer")).getFile();
        System.out.println("url=" + url);
        if (!url.contains("registration") && !url.contains("login")) {
            HttpSession session = req.getSession();
            session.setAttribute("backUrl", url);
        }
//        System.out.println(req.getSession().getAttribute("backUrl"));

        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
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
    }
}
