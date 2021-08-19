package org.aleks4ay.hotel.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.MalformedURLException;
import java.net.URL;

class Language implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        final String language = request.getParameter("language");

        HttpSession session = request.getSession();
        session.setAttribute("language", language);

        if (session.getAttribute("user") != null) {
            try {
                return "redirect:" + new URL(request.getHeader("Referer")).getFile();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return "WEB-INF/index.jsp";
    }
}
