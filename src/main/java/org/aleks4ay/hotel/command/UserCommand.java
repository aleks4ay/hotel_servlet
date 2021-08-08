package org.aleks4ay.hotel.command;

import org.aleks4ay.hotel.model.*;
import org.aleks4ay.hotel.service.OrderService;
import org.aleks4ay.hotel.service.RoomService;
import org.aleks4ay.hotel.service.UserService;

import javax.servlet.http.HttpServletRequest;
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
        if (action.equalsIgnoreCase("changeBill")) {
            int number = Integer.parseInt(request.getParameter("addBill"));
            user.setBill(user.getBill() + number);
            new UserService().update(user);

            return "redirect:/user?action=bill";
        }

        request.setAttribute("action", action);

        if (action.equalsIgnoreCase("room")) {
            List<Room> roomList = new RoomService().getAll(POSITION_ON_PAGE, Integer.parseInt(page));
            request.setAttribute("rooms", roomList);

        } else if (action.equalsIgnoreCase("account")) {
            action = "order";
            request.setAttribute("action", action);

        } else if (action.equalsIgnoreCase("order")) {
            System.out.println("user before = "+ user);
            List<Order> orderList = new OrderService().getAllByUser(user);
            System.out.println("user after = "+ user);
            request.setAttribute("orders", orderList);

        } else if (action.equalsIgnoreCase("proposal")) {
//            List<Proposal> proposalList = new ProposalService().getAll();
//            request.setAttribute("proposals", proposalList);

        } else if (action.equalsIgnoreCase("bill")) {
            request.setAttribute("bill", user.getBill());

        }
        return "WEB-INF/jsp/userPage.jsp";
    }
}