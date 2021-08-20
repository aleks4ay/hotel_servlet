package org.aleks4ay.hotel.command;

import org.aleks4ay.hotel.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

class Logout implements Command {
    private static final Logger log = LogManager.getLogger(Logout.class);

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") != null) {
            log.info("User '{}' was logged out.", ((User) request.getSession().getAttribute("user")).getLogin());
            request.getSession().invalidate();
            request.setAttribute("userType", "guest");
        }
        return "WEB-INF/jsp/index.jsp";
    }
}
