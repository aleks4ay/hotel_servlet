package org.aleks4ay.hotel.command;

import javax.servlet.http.HttpServletRequest;

interface Command {
    String execute(HttpServletRequest request);
}
