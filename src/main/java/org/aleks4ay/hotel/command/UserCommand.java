package org.aleks4ay.hotel.command;

import org.aleks4ay.hotel.dao.ConnectionPool;
import org.aleks4ay.hotel.exception.NotEmptyRoomException;
import org.aleks4ay.hotel.model.*;
import org.aleks4ay.hotel.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class UserCommand implements Command {
    private static final int POSITION_ON_PAGE = 4;
    private final ConnectionPool connectionPool = new ConnectionPool();
    private static final Logger log = LogManager.getLogger(UserCommand.class);
    private final OrderService orderService = new OrderService(connectionPool);
    private final RoomService roomService = new RoomService(connectionPool);
    private final UserService userService = new UserService(connectionPool);
    private final InvoiceService invoiceService = new InvoiceService(connectionPool);

    @Override
    public String execute(HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");

        String action = request.getParameter("action");
        request.setAttribute("action", action);
        request.setAttribute("itemOnPage", POSITION_ON_PAGE);
        request.setAttribute("categories", Category.values());

        if (action.equalsIgnoreCase("room") || action.contains("room")) {
            return getRoom(request);
        }
        if (action.equalsIgnoreCase("setDate")) {
            return setDate(request);
        }
        if (action.equalsIgnoreCase("booking")) {
            return doBooking(request);
        }
        if (action.equalsIgnoreCase("newProposal")) {
            return doNewProposal(request, user);
        }
        if (action.equalsIgnoreCase("newOrder")) {
            return doNewOrder(request, user);
        }
        if (action.equalsIgnoreCase("filter")) {
            return doFiltering(request);
        }
        if (action.equalsIgnoreCase("changeBill")) {
            return doChangeBill(request, user);
        }
        if (action.equalsIgnoreCase("account")) {
            return doAccount(request, user);
        }
        return "WEB-INF/jsp/userPage.jsp";
    }

    private String setDate(HttpServletRequest request) {
        return "redirect:/user?action=room";
    }


    private String doChangeBill(HttpServletRequest request, User user) {
        int number = Integer.parseInt(request.getParameter("addBill"));
        user.addBill(number);
        userService.update(user);
        return "redirect:/user?action=account&ap=bill";
    }


    private String getRoom(HttpServletRequest request) {
        List<Room> roomList = roomService.getRoomsWithFilter(request);
        roomList = roomService.doPagination(POSITION_ON_PAGE, (int) request.getAttribute("pg"), roomList);
        request.setAttribute("rooms", roomList);
        return "WEB-INF/jsp/userPage.jsp";
    }


    private String doFiltering(HttpServletRequest request) {
        List<String> filters = new ArrayList<>();

        String filterButtonName = request.getParameter("filter");

        if (filterButtonName != null && filterButtonName.equalsIgnoreCase("filterCansel")) {
            request.removeAttribute("category");
            request.removeAttribute("guests");
            request.getSession().removeAttribute("category");
            request.getSession().removeAttribute("guests");
            request.getSession().removeAttribute("arrival");
            request.getSession().removeAttribute("departure");
        } else {
            if (!request.getParameter("filter_category").equalsIgnoreCase("Select Category")) {
                Category category = Category.valueOf(request.getParameter("filter_category"));
                request.getSession().setAttribute("category", category);
                filters.add(" category = '" + request.getParameter("filter_category") + "'");
            }
            if (!request.getParameter("filter_guests").equals("0")) {
                request.getSession().setAttribute("guests", Integer.parseInt(request.getParameter("filter_guests")));
                filters.add(" guests = " + request.getParameter("filter_guests"));
            }
            request.setAttribute("filters", filters);
        }
        return "redirect:/user?action=room";
    }


    private String doAccount(HttpServletRequest request, User user) {
        String actionPage = request.getParameter("ap");
        if (actionPage == null) {
            actionPage = "order";
        }
        if (actionPage.equalsIgnoreCase("order")) {
            List<Order> orderList = orderService.findAllByUser(user);
            user.setOrders(orderList);
            request.setAttribute("orders", orderList);
        } else if (actionPage.equalsIgnoreCase("bill")) {
            request.setAttribute("bill", user.getBill());
        }
        request.setAttribute("ap", actionPage);
        return "WEB-INF/jsp/userPage.jsp";
    }


    private String doNewProposal(HttpServletRequest request, User user) {
        Order order = orderService.createProposal(request, user);
        orderService.save(order);
        return "redirect:/user?action=account&ap=order";
    }


    private String doNewOrder(HttpServletRequest request, User user) {
        long id = Long.parseLong(request.getParameter("id"));
        OrderDto dto = orderService.createOrderDto(request, roomService.getById(id));
        Order order = orderService.create(dto, user);
        try {
            orderService.saveOrderIfEmpty(order);
            log.info("Was create new Order '{}'", order);
            Invoice invoice = invoiceService.create(new Invoice(LocalDateTime.now(), Invoice.Status.NEW, order));
            log.info("Was create new Invoice '{}'", invoice);
        } catch (NotEmptyRoomException e) {
            request.getSession().setAttribute("arrival", dto.getArrival());
            request.getSession().setAttribute("departure", dto.getDeparture());
            request.setAttribute("roomOccupiedMessage", e);
            request.setAttribute("id", id);
            return doBooking(request);
        }
        return "redirect:/user?action=account&ap=order";
    }


    private String doBooking(HttpServletRequest request) {
        long id = Long.parseLong(request.getParameter("id"));
        request.setAttribute("orderDto", orderService.createOrderDto(request, roomService.getById(id)));
        request.setAttribute("roomId", id);
        return "WEB-INF/jsp/usr/u_booking.jsp";
    }
}