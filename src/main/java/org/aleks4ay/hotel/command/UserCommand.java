package org.aleks4ay.hotel.command;

import org.aleks4ay.hotel.model.*;
import org.aleks4ay.hotel.service.OrderService;
import org.aleks4ay.hotel.service.ProposalService;
import org.aleks4ay.hotel.service.RoomService;
import org.aleks4ay.hotel.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class UserCommand implements Command {
    private static int POSITION_ON_PAGE = 10;

    @Override
    public String execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        String action = request.getParameter("action");
        String page = request.getParameter("pg");
        if (page == null) {
            page = "1";
        }
        request.setAttribute("pg", page);
        request.setAttribute("itemOnPage", POSITION_ON_PAGE);

        if (action == null) {
            action = "room";
        }

        if (action.equalsIgnoreCase("newProposal")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateStartString = request.getParameter("arrival");
            String dateEndString = request.getParameter("departure");
            LocalDate dateStart, dateEnd;
            if (dateEndString.isEmpty() || dateEndString.isEmpty()){
                dateStart = LocalDate.now();
                dateEnd = dateStart.plusDays(1);
            } else {
                dateStart = LocalDate.parse(dateStartString, formatter);
                dateEnd = LocalDate.parse(dateEndString, formatter);
            }

            int guests = Integer.parseInt(request.getParameter("field1"));
            Category category = Category.valueOf(request.getParameter("field2"));
            new ProposalService().create(new Proposal(dateStart, dateEnd, guests, category, user));
            return "redirect:/user?action=proposal";
        }

        if (action.equalsIgnoreCase("changeBill")) {
            int number = Integer.parseInt(request.getParameter("addBill"));
            user.setBill(user.getBill() + number);
            new UserService().update(user);

            return "redirect:/user?action=bill";
        }

        if (action.equalsIgnoreCase("account")) {
            action = "order";
        }

        request.setAttribute("action", action);
        request.setAttribute("categories", Category.values());

        if (action.equalsIgnoreCase("room")) {
            List<Room> roomList = new RoomService().getAll(POSITION_ON_PAGE, Integer.parseInt(page));
            request.setAttribute("rooms", roomList);

        } else if (action.equalsIgnoreCase("order")) {
            List<Order> orderList = new OrderService().getAllByUser(user);
            user.setOrders(orderList);
            request.setAttribute("orders", orderList);

        } else if (action.equalsIgnoreCase("proposal")) {
            List<Proposal> proposalList = new ProposalService().getAllByUser(user);
            request.setAttribute("proposals", proposalList);

        } else if (action.equalsIgnoreCase("bill")) {
            request.setAttribute("bill", user.getBill());

        }
        return "WEB-INF/jsp/userPage.jsp";
    }
}