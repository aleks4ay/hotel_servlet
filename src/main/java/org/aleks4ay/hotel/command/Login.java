package org.aleks4ay.hotel.command;

import org.aleks4ay.hotel.dao.ConnectionPool;
import org.aleks4ay.hotel.exception.NotFoundException;
import org.aleks4ay.hotel.model.Role;
import org.aleks4ay.hotel.model.User;
import org.aleks4ay.hotel.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;

class Login implements Command {
    private static final Logger log = LogManager.getLogger(Login.class);
    private UserService userService = new UserService(new ConnectionPool());

    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        String pass = request.getParameter("log_pass");

        if ( (login == null || login.isEmpty()) && (pass == null || pass.isEmpty()) ) {
            log.info("Try new entered");
            CommandUtils.addBackUrl(request);
            return "WEB-INF/jsp/login.jsp";
        }
        User user;
        try {
            user = userService.findByLoginAndPassword(login, pass);
            request.getSession().setAttribute("user", user);
        } catch (NotFoundException e) {
            log.info("Login '{}' or password is wrong!", login);
            request.setAttribute("wrongPass", "Password is wrong!");
            request.setAttribute("oldLogin", login);
            return "WEB-INF/jsp/login.jsp";
        }

        if (user.getRole() == Role.ROLE_ADMIN) {
            request.setAttribute("action", "user");
            log.info("Admin '{}' exited on site", login);
            return "redirect:/admin?action=room";
        }
        if (user.getRole() == Role.ROLE_MANAGER) {
            request.setAttribute("action", "order");
            log.info("Manager '{}' exited on site", login);
            return "redirect:/manager?action=order";
        }
        if (user.getRole() == Role.ROLE_USER) {
            request.setAttribute("action", "room");
            log.info("User '{}' exited on site", login);
            return "redirect:/user?action=room";
        }
        return "WEB-INF/jsp/index.jsp";
    }
}
