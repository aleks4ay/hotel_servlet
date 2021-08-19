package org.aleks4ay.hotel.command;

import org.aleks4ay.hotel.dao.ConnectionPool;
import org.aleks4ay.hotel.exception.AlreadyException;
import org.aleks4ay.hotel.model.User;
import org.aleks4ay.hotel.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

class Registration implements Command {
    private static final Logger log = LogManager.getLogger(Registration.class);
    private UserService userService = new UserService(new ConnectionPool());

    @Override
    public String execute(HttpServletRequest request) {
        final String method = request.getMethod();
        if (method.equalsIgnoreCase("get")) {
            CommandUtils.addBackUrl(request);
            return "WEB-INF/jsp/registration.jsp";
        }
        String login = request.getParameter("loginNew");
        String pass = request.getParameter("log_pass");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        try {
            userService.save(new User(0L, login, firstName, lastName, pass));
            log.info("User '{}' was registered on site", login);
            return "redirect:/login";
        } catch (AlreadyException e) {
            request.setAttribute("warnLogin", "exist");
            request.setAttribute("oldLogin", login);
            request.setAttribute("oldFirstName", firstName);
            request.setAttribute("oldLastName", lastName);
            return "/WEB-INF/jsp/registration.jsp";
        }
    }
}
