package org.aleks4ay.hotel.command;

import org.aleks4ay.hotel.model.*;
import org.aleks4ay.hotel.service.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

class UserCommand implements Command {
    private static final int POSITION_ON_PAGE = 10;

    @Override
    public String execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if(user == null) {
            return "/WEB-INF/index.jsp";
        }

        String action = request.getParameter("action");
        String page = request.getParameter("pg");
        if (page == null) {
            page = "1";
        }
        request.setAttribute("pg", page);
        request.setAttribute("itemOnPage", POSITION_ON_PAGE);
        request.setAttribute("categories", Category.values());

        if (action == null) {
            action = "room";
        }

        if (action.equalsIgnoreCase("booking")) {
            return getBooking(request);
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

        // TODO: 09.08.2021
        if (action.equalsIgnoreCase("filter")) {
            List<String> filters = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateStartString = request.getParameter("filter_arrival");
            String dateEndString = request.getParameter("filter_departure");
            LocalDate dateStart, dateEnd;
            if (!dateStartString.isEmpty()) {
                dateStart = LocalDate.parse(dateStartString, formatter);

                if (!dateEndString.isEmpty()) {
                    dateEnd = LocalDate.parse(dateEndString, formatter);
                    filters.add("date BEETWEN " + dateStart + " and " + dateEnd);
                } else {
                    filters.add("date = " + dateStart);
                }
            }
            String categoryString = request.getParameter("filter_category");
            String guestsString = request.getParameter("filter_guests");
            request.setAttribute("category", categoryString);
            request.setAttribute("guests", guestsString);

            filters.add(" guests = " + guestsString);
            filters.add(" category = '" + categoryString + "'");

            List<Room> roomsAfterFilter = new RoomService().getAllWithFilters(filters);

            request.setAttribute("action", "filter");
            request.setAttribute("rooms", roomsAfterFilter);
            return "WEB-INF/jsp/userPage.jsp";

        }
        // TODO: 09.08.2021


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

    private String getBooking(HttpServletRequest request) {
        System.out.println("Booking");
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("action", "booking");
        long id = Long.parseLong(request.getParameter("id"));
        Order order = new Order();
        order.setUser(user);
        order.setRoom(new RoomService().getById(id));

        LocalDate dateStart = null;
        LocalDate dateEnd = null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateStartString = request.getParameter("date_arrival");
        String dateEndString = request.getParameter("date_departure");
        if (dateStartString != null) {
            dateStart = LocalDate.parse(dateStartString, formatter);
            if (dateEndString != null) {
                dateEnd = LocalDate.parse(dateEndString, formatter);
            } else if (dateEndString == null) {
                dateEnd = dateStart.plusDays(1);
            }
        } else if (dateEndString != null) {
            dateEnd = LocalDate.parse(dateEndString, formatter);
            dateStart = dateEnd.minusDays(1);
        } else {
            dateStart = LocalDate.now();
            dateEnd = dateStart.plusDays(1);
        }

        order.setArrival(dateStart);
        order.setDeparture(dateEnd);
        request.setAttribute("order", order);

        /*LocalDate dateStart, dateEnd;
        if (dateEndString.isEmpty() || dateEndString.isEmpty()){
            dateStart = LocalDate.now();
            dateEnd = dateStart.plusDays(1);
        } else {

            dateEnd = LocalDate.parse(dateEndString, formatter);
        }

        int guests = Integer.parseInt(request.getParameter("field1"));
        Category category = Category.valueOf(request.getParameter("field2"));
        new ProposalService().create(new Proposal(dateStart, dateEnd, guests, category, user));
        return "redirect:/user?action=proposal";
        date_arrival*/

//        return "WEB-INF/jsp/userPage.jsp";
        return "WEB-INF/jsp/userPage.jsp";
    }
}