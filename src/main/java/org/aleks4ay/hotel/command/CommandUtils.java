package org.aleks4ay.hotel.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.MalformedURLException;
import java.net.URL;

final class CommandUtils {

    static void addBackUrl(HttpServletRequest request) {
        try {
            String url = new URL(request.getHeader("Referer")).getFile();

            if (!url.contains("registration") && !url.contains("login") ) {
                HttpSession session = request.getSession();
                session.setAttribute("backUrl", url);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
