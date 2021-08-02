package org.aleks4ay.hotel.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URL;

@WebServlet("/lang")
public class LangServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        final String language = req.getParameter("language");
//        System.out.println("lang=" + language);
        String url = new URL(req.getHeader("Referer")).getFile();

        HttpSession session = req.getSession();
        session.setAttribute("language", language);

        resp.sendRedirect(url);
    }
}
