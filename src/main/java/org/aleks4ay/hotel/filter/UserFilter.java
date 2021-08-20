package org.aleks4ay.hotel.filter;

import org.aleks4ay.hotel.model.Role;
import org.aleks4ay.hotel.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter( urlPatterns = { "/user" })
public class UserFilter implements Filter {
    private static final Logger log = LogManager.getLogger(UserFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession(false);
        boolean isGuest = session != null && session.getAttribute("user") == null;
        boolean isUser = session != null && session.getAttribute("user") != null
                && ((User) session.getAttribute("user")).getRole() == Role.ROLE_USER;

        if( (isGuest && !request.getQueryString().contains("account"))  ||  isUser ) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            log.warn("Attempting to bypass the security system! Bad request");
            ((HttpServletResponse) servletResponse).sendRedirect("/");
        }
    }

    @Override
    public void destroy() {

    }
}
