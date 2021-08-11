package org.aleks4ay.hotel.command;

import org.aleks4ay.hotel.model.*;
import org.aleks4ay.hotel.service.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

class UserCommand implements Command {
    private static final int POSITION_ON_PAGE = 10;

    @Override
    public String execute(HttpServletRequest request) {
        String userType = (String) request.getAttribute("userType");

        User user = (User) request.getSession().getAttribute("user");

        String action = request.getParameter("action");
        request.setAttribute("action", action);
        request.setAttribute("itemOnPage", POSITION_ON_PAGE);
        request.setAttribute("categories", Category.values());

        //user?action=account when 'NotUser'
        if(     (userType.equalsIgnoreCase("guest") || user == null)
                && !action.equalsIgnoreCase("room")
                && !action.equalsIgnoreCase("filter") ) {
            return "/WEB-INF/index.jsp";
        }
        //user?action=
        if (action == null) {
            action = "room"; // TODO: 10.08.2021 delete?
        }
        //user?action=room
        if (action.equalsIgnoreCase("room")) {
            RoomService roomService = new RoomService();
            List<Room> roomList;
            if (request.getAttribute("filters") != null) {
                roomList = roomService.getAllWithFilters((List<String>) request.getAttribute("filters"));
            } else {
                roomList = roomService.getAll();
            }
            roomList = roomService.doPagination(POSITION_ON_PAGE, (int) request.getAttribute("pg"), roomList);

            request.setAttribute("rooms", roomList);
        }
        //user?action=booking
        if (action.equalsIgnoreCase("booking")) {
            return doBooking(request);
        }
        //user?action=newProposal
        if (action.equalsIgnoreCase("newProposal")) {
            return doNewProposal(request, user);
        }
        //user?action=newOrder&id=22
        if (action.equalsIgnoreCase("newOrder")) {
            return doNewOrder(request, user);
        }
        //user?action=filter
        if (action.equalsIgnoreCase("filter")) {
            return doFiltering(request);
        }
        //user?action=changeBill
        if (action.equalsIgnoreCase("changeBill")) {
            return doChangeBill(request, user);
        }
        //user?action=account&ap=***
        if (action.equalsIgnoreCase("account")) {
//            action = "order";
//            request.setAttribute("action", action);
            return doAccount(request, user);
        }
        return "WEB-INF/jsp/userPage.jsp";
    }


/*
    private String getGuestPage(HttpServletRequest request) {

        List<Room> roomList = new RoomService().getAll(POSITION_ON_PAGE, Integer.parseInt(page));
        request.setAttribute("rooms", roomList);
        request.setAttribute("action", "room");
        return "/WEB-INF/jsp/userPage.jsp";
    }*/

    private String doChangeBill(HttpServletRequest request, User user) {
        int number = Integer.parseInt(request.getParameter("addBill"));
        user.setBill(user.getBill() + number);
        new UserService().update(user);
        return "redirect:/user?action=account&ap=bill";
    }


    private String doFiltering(HttpServletRequest request) {
        List<String> filters = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateStartString = request.getParameter("filter_arrival");
        String dateEndString = request.getParameter("filter_departure");

/*        System.out.println("dateStartString from filter = " + dateStartString);
        System.out.println("dateEndString from filter = " + dateEndString);*/
        LocalDate dateStart = null;
        LocalDate dateEnd = null;

        if (!dateStartString.isEmpty()) {
            dateStart = LocalDate.parse(dateStartString, formatter);
        }
        if (!dateEndString.isEmpty()) {
            dateEnd = LocalDate.parse(dateEndString, formatter);
        }

        String categoryString = request.getParameter("filter_category");
        String guestsString = request.getParameter("filter_guests");

        request.setAttribute("category", request.getParameter("filter_category"));
        request.setAttribute("guests", Integer.parseInt(guestsString));
        request.setAttribute("arrival", dateStart);
        request.setAttribute("departure", dateEnd);

        filters.add(" guests = " + guestsString);
        filters.add(" category = '" + categoryString + "'");
        request.setAttribute("filters", filters);

        return "/user?action=room";
    }


    private String doAccount(HttpServletRequest request, User user) {
        String actionPage = request.getParameter("ap");
        //user?action=account&ap=
        if (actionPage == null) {
            actionPage = "order";
        }
        //user?action=account&ap=order
        if (actionPage.equalsIgnoreCase("order")) {
            List<Order> orderList = new OrderService().getAllByUser(user);
            user.setOrders(orderList);
            request.setAttribute("orders", orderList);
        //user?action=account&ap=proposal
        } else if (actionPage.equalsIgnoreCase("proposal")) {
            List<Proposal> proposalList = new ProposalService().getAllByUser(user);
            request.setAttribute("proposals", proposalList);
        //user?action=account&ap=bill
        } else if (actionPage.equalsIgnoreCase("bill")) {
            request.setAttribute("bill", user.getBill());

        }
        request.setAttribute("ap", actionPage);
        return "WEB-INF/jsp/userPage.jsp";
    }


    private String doNewProposal(HttpServletRequest request, User user) {
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
        return "redirect:/user?action=account&ap=proposal";
    }


    private String doNewOrder(HttpServletRequest request, User user) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateStartString = request.getParameter("date_arrival");
        String dateEndString = request.getParameter("date_departure");
        LocalDate dateStart = LocalDate.parse(dateStartString, formatter);
        LocalDate dateEnd = LocalDate.parse(dateEndString, formatter);

        long room_id = Long.parseLong(request.getParameter("id"));
        new OrderService().create(room_id, dateStart, dateEnd, user);
        return "redirect:/user?action=account&ap=order";
    }


    private String doBooking(HttpServletRequest request) {
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

//        order.setArrival(dateStart);
//        order.setDeparture(dateEnd);
        request.setAttribute("order", order);

        return "WEB-INF/jsp/userPage.jsp";
    }

    // TODO: 10.08.2021  getDateStart(),  getDateEnd()
}