package org.aleks4ay.hotel.command;

import org.aleks4ay.hotel.dao.ConnectionPool;
import org.aleks4ay.hotel.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@MultipartConfig
public class ServletController extends HttpServlet{
    private final Map<String, Command> commands = new HashMap<>();
    private static final Logger log = LogManager.getLogger(ServletController.class);

    @Override
    public void init() {
        commands.put("lang", new Language());
        commands.put("login", new Login());
        commands.put("logout", new Logout());
        commands.put("registration", new Registration());
        commands.put("admin", new AdminCommand());
        commands.put("manager", new ManagerCommand());
        commands.put("user", new UserCommand());
        commands.put("exception", new CommandException());
        startInvoiceInspector();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getRequestURI().replaceAll(".*(/app)?/", "");
        Command command = commands.getOrDefault(path, (r)->"/WEB-INF/jsp/index.jsp");
        String urlPage = command.execute(request);
        if (urlPage.contains("redirect:")) {
            log.debug("redirect to=" + urlPage.replaceAll("redirect:", ""));
            response.sendRedirect(urlPage.replaceAll("redirect:", ""));
        } else {
            log.debug("forward to=" + urlPage);
            request.getRequestDispatcher(urlPage).forward(request, response);
        }
    }

    private void startInvoiceInspector() {
        new Thread(() -> {
            while (Thread.currentThread().isAlive()) {
                try {
                    Thread.sleep(5 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new OrderService(new ConnectionPool()).setCancelInvoice();
            }
        }).start();
    }
}
