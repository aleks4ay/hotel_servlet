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
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

@WebServlet(urlPatterns = "/registration")
public class RegistrationServlet extends HttpServlet {
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
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        User user = new User(null, login, firstName, lastName, pass);

        boolean isEmptyLogin = login.equals("");
        boolean isEmptyPass = pass.equals("");
        boolean isEmptyFirstName = firstName.equals("");
        boolean isEmptyLastName = lastName.equals("");
        if (isEmptyLogin) {
            req.setAttribute("warnLogin2", "empty field");
        }
        if (isEmptyPass) {
            req.setAttribute("warnPass", "empty field");
        }
        if (isEmptyFirstName) {
            req.setAttribute("warnFirstName", "empty field");
        }
        if (isEmptyLastName) {
            req.setAttribute("warnLastName", "empty field");
        }
        if (isEmptyLogin || isEmptyPass || isEmptyFirstName || isEmptyLastName) {
            req.setAttribute("oldLogin", login);
            req.setAttribute("oldFirstName", firstName);
            req.setAttribute("oldLastName", lastName);
            req.getRequestDispatcher("/WEB-INF/jsp/newUser.jsp").forward(req, resp);
        } else {

            boolean success = userService.create(user);

            if (success) {
                resp.sendRedirect("/login");
            } else {
                req.setAttribute("warnLogin", "exist");
                req.setAttribute("oldLogin", login);
                req.setAttribute("oldFirstName", firstName);
                req.setAttribute("oldLastName", lastName);
                req.getRequestDispatcher("/WEB-INF/jsp/newUser.jsp").forward(req, resp);
            }
        }
    }
}
