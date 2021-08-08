package org.aleks4ay.hotel.command;

import javax.servlet.http.HttpServletRequest;

public class CommandException implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        throw new RuntimeException("Generated CommandException");
    }
}
