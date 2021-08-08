package org.aleks4ay.hotel.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.MalformedURLException;
import java.net.URL;

public class Language implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        final String language = request.getParameter("language");
/*        String path = null;
        try {
            path = new URL(request.getHeader("Referer")).getFile();
            System.out.println("path = " + path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }*/
//        String path = request.getRequestURI();

        HttpSession session = request.getSession();
        session.setAttribute("language", language);

        return "WEB-INF/index.jsp";
    }
}
