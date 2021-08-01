package org.aleks4ay.hotel.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URL;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        final String language = req.getParameter("language");
//        System.out.println("lang=" + language);
        String url = new URL(req.getHeader("Referer")).getFile();

        HttpSession session = req.getSession();
        session.removeAttribute("user");

        resp.sendRedirect(url);
    }
}
