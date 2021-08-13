package org.aleks4ay.hotel.command;

import org.aleks4ay.hotel.model.Role;
import org.aleks4ay.hotel.model.User;
import org.aleks4ay.hotel.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

class Login implements Command {
    private static final Logger log = LogManager.getLogger(Login.class);
    private UserService userService = new UserService();

    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        String pass = request.getParameter("log_pass");

        if ( (login == null || login.isEmpty()) && (pass == null || pass.isEmpty()) ) {
            log.info("Try new exit");
            CommandUtils.addBackUrl(request);
            return "WEB-INF/jsp/login.jsp";
        }
        if (!userService.checkLogin(login)) {
            log.info("Login '{}' is wrong!", login);
            request.setAttribute("wrongLogin", "Login is wrong!");
            request.setAttribute("oldLogin", login);
            return "WEB-INF/jsp/login.jsp";
        }

        if (!userService.checkPassword(login, pass)) {
            log.info("Login '{}' or password is wrong!", login);
            request.setAttribute("wrongPass", "Password is wrong!");
            request.setAttribute("oldLogin", login);
            return "WEB-INF/jsp/login.jsp";
        } else {
            Optional<User> userOptional = userService.getByLogin(login);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                request.getSession().setAttribute("user", user);
                if (user.getRole() == Role.ROLE_ADMIN) {
                    request.setAttribute("action", "user");
                    log.info("Admin '{}' exited on site", login);
                    return "redirect:/admin";
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
            }
            return "WEB-INF/index.jsp";
        }
    }
}
