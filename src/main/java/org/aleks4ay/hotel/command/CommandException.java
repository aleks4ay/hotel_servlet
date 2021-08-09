package org.aleks4ay.hotel.command;

import javax.servlet.http.HttpServletRequest;

class CommandException implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        throw new RuntimeException("Generated CommandException");
    }
}
