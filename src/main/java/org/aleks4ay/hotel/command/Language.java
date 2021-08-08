package org.aleks4ay.hotel.command;

import org.aleks4ay.hotel.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.MalformedURLException;
import java.net.URL;

public class Language implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        final String language = request.getParameter("language");

        HttpSession session = request.getSession();
        session.setAttribute("language", language);

        User user = (User) session.getAttribute("user");
        if (user != null) {
            try {
                String path = new URL(request.getHeader("Referer")).getFile();
                return "redirect:" + path;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return "WEB-INF/index.jsp";
    }
}
