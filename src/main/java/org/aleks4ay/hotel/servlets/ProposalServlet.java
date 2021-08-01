package org.aleks4ay.hotel.servlets;

import org.aleks4ay.hotel.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet(urlPatterns = "/proposal")
public class ProposalServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String user = req.getRemoteUser();
        System.out.println("user = " + user);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateStartString = req.getParameter("arrival");
        String dateEndString = req.getParameter("departure");
        LocalDate dateStart = LocalDate.parse(dateStartString, formatter);
        LocalDate dateEnd = LocalDate.parse(dateEndString, formatter);

        int guests = Integer.parseInt(req.getParameter("field1"));
        String selected = req.getParameter("field2");
        Category category = Category.valueOf(selected);
        Proposal proposal = new Proposal(dateStart, dateEnd, guests, category, new User(), true);

        System.out.println("dateStart " + dateStart);
        System.out.println("dateEnd " + dateEnd);
        System.out.println("guests " + guests);
        System.out.println("category " + category);

        resp.sendRedirect("/home");
    }
}
