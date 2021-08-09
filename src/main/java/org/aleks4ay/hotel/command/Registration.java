package org.aleks4ay.hotel.command;

import org.aleks4ay.hotel.model.User;
import org.aleks4ay.hotel.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

class Registration implements Command {
    private static final Logger log = LogManager.getLogger(Registration.class);
    private UserService userService = new UserService();

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

        boolean isWarn = false;
        if (login.isEmpty()) {
            isWarn = true;
            request.setAttribute("warnLogin2", "empty field");
        }
        if (pass.isEmpty()) {
            isWarn = true;
            request.setAttribute("warnPass", "empty field");
        }
        if (firstName.isEmpty()) {
            isWarn = true;
            request.setAttribute("warnFirstName", "empty field");
        }
        if (lastName.isEmpty()) {
            isWarn = true;
            request.setAttribute("warnLastName", "empty field");
        }
        if (isWarn) {
            request.setAttribute("oldLogin", login);
            request.setAttribute("oldFirstName", firstName);
            request.setAttribute("oldLastName", lastName);
            return "/WEB-INF/jsp/registration.jsp";
        } else {
            User createdUser = userService.create(login, firstName, lastName, pass);
            System.out.println("createdUser 1 = " + createdUser);

            if (createdUser != null) {
                log.info("User '{}' was registered on site", login);
                return "redirect:/login";
            } else {
                request.setAttribute("warnLogin", "exist");
                request.setAttribute("oldLogin", login);
                request.setAttribute("oldFirstName", firstName);
                request.setAttribute("oldLastName", lastName);
                return "/WEB-INF/jsp/registration.jsp";
            }
        }
    }
}
