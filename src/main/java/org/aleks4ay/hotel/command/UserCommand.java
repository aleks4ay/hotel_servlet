package org.aleks4ay.hotel.command;

import org.aleks4ay.hotel.dao.ConnectionPool;
import org.aleks4ay.hotel.exception.NoMoneyException;
import org.aleks4ay.hotel.exception.NotEmptyRoomException;
import org.aleks4ay.hotel.model.*;
import org.aleks4ay.hotel.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

class UserCommand implements Command {
    private static final Logger log = LogManager.getLogger(UserCommand.class);

    private static final int POSITION_ON_PAGE = 4;
    private final ConnectionPool connectionPool = new ConnectionPool();
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
        if (action.equalsIgnoreCase("booking")) {
            return doBooking(request);
        }
        if (action.equalsIgnoreCase("newProposal")) {
            return doNewProposal(request, user);
        }
        if (action.equalsIgnoreCase("newOrder")) {
            return doNewOrder(request, user);
        }
        if (action.equalsIgnoreCase("changeOrder")) {
            return changeOrderStatus(request, user);
        }
        if (action.equalsIgnoreCase("filter")) {
            return Utils.doFiltering(request, "/user?action=room");
        }
        if (action.equalsIgnoreCase("changeBill")) {
            return doChangeBill(request, user);
        }
        if (action.equalsIgnoreCase("account")) {
            return doAccount(request, user);
        }
        return "WEB-INF/jsp/userPage.jsp";
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


    private String doAccount(HttpServletRequest request, User user) {
        String actionPage = request.getParameter("ap");
        if (actionPage == null) {
            actionPage = "order";
        }
        request.setAttribute("ap", actionPage);
        if (actionPage.equalsIgnoreCase("order")) {
            List<Order> orderList = orderService.findAllByUser(user);
            request.setAttribute("orders", orderList);
        } else if (actionPage.equalsIgnoreCase("bill")) {
            user.setBill(userService.findById(user.getId()).getBill());
            request.setAttribute("bill", user.getBill());
        } else if (actionPage.equalsIgnoreCase("oneOrder")) {
            return getOrderBlank(request, user);
        }
        return "WEB-INF/jsp/userPage.jsp";
    }


    private String doNewProposal(HttpServletRequest request, User user) {
        Order order = orderService.createProposal(request, user);
        orderService.save(order);
        return "redirect:/user?action=account&ap=order";
    }


    private String doNewOrder(HttpServletRequest request, User user) {
        long id = Long.parseLong(request.getParameter("id"));
        OrderDto dto = orderService.createOrderDto(request, roomService.findById(id));
        Order order = orderService.create(dto, user);
        try {
            order = orderService.saveOrderIfEmpty(order);
            log.info("Was create new Order '{}'", order);
            Invoice invoice = invoiceService.create(new Invoice(LocalDateTime.now(), Invoice.Status.NEW, order));
            log.info("Was create new Invoice '{}'", invoice);
        } catch (NotEmptyRoomException e) {
            user.getOrders().remove(order);
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
        request.setAttribute("orderDto", orderService.createOrderDto(request, roomService.findById(id)));
        request.setAttribute("roomId", id);
        return "WEB-INF/jsp/usr/u_booking.jsp";
    }

    private String getOrderBlank(HttpServletRequest request, User user) {
        request.setAttribute("categories", Category.values());
        request.setAttribute("order", orderService.findById(Long.parseLong(request.getParameter("id"))));
        request.setAttribute("bill", user.getBill());
        return "WEB-INF/jsp/usr/u_one_order.jsp";
    }

    private String changeOrderStatus(HttpServletRequest request, User user) {
        Order order = user.getOrderById(Long.parseLong(request.getParameter("id")));
        if (request.getParameter("changeStatus").equalsIgnoreCase("confirm")) {
            order.setStatus(Order.Status.CONFIRMED);
            orderService.updateStatus(order);
            Invoice invoice = invoiceService.create(new Invoice(LocalDateTime.now(), Invoice.Status.NEW, order));
            log.info("Was confirmed Order '{}'", order);
            log.info("Was create new Invoice '{}'", invoice);
        } else {
            try {
                orderService.pay(order);
            } catch (NoMoneyException | SQLException e) {
                request.setAttribute("noMoneyMessage", e);
                return getOrderBlank(request, user);
            }
        }
        return "redirect:/user?action=account&ap=oneOrder&id=" + order.getId();
    }
}