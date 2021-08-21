package org.aleks4ay.hotel.command;

import org.aleks4ay.hotel.dao.ConnectionPool;
import org.aleks4ay.hotel.model.*;
import org.aleks4ay.hotel.service.OrderService;
import org.aleks4ay.hotel.service.RoomService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

class ManagerCommand implements Command {
    private static final int POSITION_ON_PAGE = 4;
    private final ConnectionPool connectionPool = new ConnectionPool();
    private static final Logger log = LogManager.getLogger(ManagerCommand.class);
    private final OrderService orderService = new OrderService(connectionPool);
    private final RoomService roomService = new RoomService(connectionPool);

    @Override
    public String execute(HttpServletRequest request) {
        String action = request.getParameter("action");
        request.setAttribute("action", action);
        request.setAttribute("itemOnPage", POSITION_ON_PAGE);
        request.setAttribute("categories", Category.values());

        if (action.equalsIgnoreCase("order")) {
            return getOrders(request);
        }
        if (action.equalsIgnoreCase("room")) {
            return getRooms(request);
        }
        if (action.equalsIgnoreCase("manageProposal")) {
            return manageOrder(request);
        }
        if (action.equalsIgnoreCase("newOrder")) {
            return doNewOrder(request);
        }
        if (action.equalsIgnoreCase("filter")) {
            return Utils.doFiltering(request, "/manager?action=room");
        }
        return "WEB-INF/jsp/managerPage.jsp";
    }

    private String getRooms(HttpServletRequest request) {
        List<Room> roomList = roomService.getRoomsWithFilter(request);
        roomList = roomService.doPagination(POSITION_ON_PAGE, (int) request.getAttribute("pg"), roomList);
        request.setAttribute("rooms", roomList);
        return "WEB-INF/jsp/managerPage.jsp";
    }

    private String getOrders(HttpServletRequest request) {
        List<Order> orderList = orderService.findAll("registered desc");
        orderList = orderService.doPagination(POSITION_ON_PAGE, (int) request.getAttribute("pg"), orderList);
        request.setAttribute("orders", orderList);
        return "WEB-INF/jsp/managerPage.jsp";
    }

    private String manageOrder(HttpServletRequest request) {
        long ordId = Long.parseLong(request.getParameter("ordId"));
        Order order = orderService.findById(ordId);
        Utils.setAttributesFromManager(request, order);
        request.setAttribute("ordId", ordId);
        request.setAttribute("isNew", order.getStatus() == Order.Status.NEW);
        request.setAttribute("action", "manageProposal");
        return getRooms(request);
    }

    private String doNewOrder(HttpServletRequest request) {
        long roomId = Long.parseLong(request.getParameter("roomId"));
        long ordId = Long.parseLong(request.getParameter("ordId"));
        Order order = orderService.findById(ordId);
        order.setRoomDeeply(roomService.findById(roomId));
        order.setStatus(Order.Status.BOOKED);
        order = orderService.updateOrderIfEmpty(order);
        log.info("Was processed Order '{}'", order);
        return "redirect:/manager?action=order";
    }
}