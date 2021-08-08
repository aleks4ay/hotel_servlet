package org.aleks4ay.hotel.command;

import org.aleks4ay.hotel.model.Category;
import org.aleks4ay.hotel.model.Room;
import org.aleks4ay.hotel.service.RoomService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

public class Booking implements Command {
    private static final int POSITION_ON_PAGE = 3;

    @Override
    public String execute(HttpServletRequest request) {

        String page = request.getParameter("pg");
        List<Category> categories = Arrays.asList(Category.values());
        request.setAttribute("categories", categories);
        if (page == null || page.equals("")) {
            page = "1";
        }
        request.setAttribute("pg", page);
        request.setAttribute("itemOnPage", POSITION_ON_PAGE);

        List<Room> roomList = new RoomService().getAll(POSITION_ON_PAGE, Integer.parseInt(page));
        request.setAttribute("rooms", roomList);
        request.setAttribute("action", "room");
        return "/WEB-INF/jsp/userPage.jsp";
    }
}
