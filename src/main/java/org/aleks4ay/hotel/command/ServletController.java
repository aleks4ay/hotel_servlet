package org.aleks4ay.hotel.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class ServletController extends HttpServlet{
    private Map<String, Command> commands = new HashMap<>();
    private static final Logger log = LogManager.getLogger(ServletController.class);

    @Override
    public void init() {
        commands.put("lang", new Language());
        commands.put("login", new Login());
        commands.put("logout", new Logout());
        commands.put("registration", new Registration());
        commands.put("booking", new Booking());
        commands.put("admin", new AdminCommand());
        commands.put("user", new UserCommand());
        commands.put("exception", new CommandException());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("Do get request to {}", req.getRequestURI());
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("Do post request to {}", req.getRequestURI());
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response)
            throws ServletException, IOException {
/*        final Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String next = headerNames.nextElement();
            System.out.println("next: " + next + " -> " + request.getHeader(next));
        }*/

        String path = request.getRequestURI();
        path = path.replaceAll(".*(/app)?/", "");
        Command command = commands.getOrDefault(path, (r)->"/WEB-INF/index.jsp");
        String urlPage = command.execute(request);
        if (urlPage.contains("redirect:")) {
            log.debug("redirect to=" + urlPage.replaceAll("redirect:", ""));
            response.sendRedirect(urlPage.replaceAll("redirect:", "")); //replaceAll("redirect:", "/app")
        } else {
            log.debug("forward to=" + urlPage);
            request.getRequestDispatcher(urlPage).forward(request, response);
        }
    }
}
