package org.aleks4ay.hotel.command;

import org.aleks4ay.hotel.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

class Logout implements Command {
    private static final Logger log = LogManager.getLogger(Logout.class);

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "WEB-INF/index.jsp";
        } else {
            log.info("User '{}' was logged out.", ((User) session.getAttribute("user")).getLogin());
            request.getSession().invalidate();
            return "WEB-INF/index.jsp";
        }
    }
}
