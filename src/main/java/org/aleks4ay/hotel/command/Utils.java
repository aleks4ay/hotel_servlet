package org.aleks4ay.hotel.command;

import org.aleks4ay.hotel.model.Category;
import org.aleks4ay.hotel.model.Order;
import org.aleks4ay.hotel.model.Room;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

class Utils {
    static void initSortMethod(HttpServletRequest request) {
        request.setAttribute("sortMethod", request.getSession().getAttribute("sortMethod") == null ? "id"
                : request.getSession().getAttribute("sortMethod"));
        request.getSession().setAttribute("sortMethod", request.getAttribute("sortMethod"));
    }

    public static String doFiltering(HttpServletRequest request, String path) {
        List<String> filters = new ArrayList<>();

        String filterButtonName = request.getParameter("filter");
        Object filterCancel = request.getAttribute("filter");

        if ( (filterButtonName != null && filterButtonName.equalsIgnoreCase("filterCancel"))
                || (filterCancel != null && ((String)filterCancel).equalsIgnoreCase("filterCancel")) ) {
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
        return "redirect:" + path;
    }

    static Room getNewRoomFromRequest(HttpServletRequest request) {
        int number = Integer.parseInt(request.getParameter("number"));
        String description = request.getParameter("description");
        long price = Long.parseLong(request.getParameter("price"));
        int guests = Integer.parseInt(request.getParameter("guests"));
        Category category = Category.valueOf(request.getParameter("category"));
        String imgName = request.getParameter("image");
        return new Room(number, category, guests, description, price, imgName);
    }

    static void setAttributesFromManager(HttpServletRequest request, Order order) {
        HttpSession session = request.getSession();
        session.setAttribute("arrival", order.getArrival());
        session.setAttribute("departure", order.getDeparture());
        session.setAttribute("guests", order.getGuests());
        session.setAttribute("category", order.getCategory());
    }
}