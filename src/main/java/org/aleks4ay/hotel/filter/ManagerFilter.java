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

@WebFilter( urlPatterns = { "/manager" })
public class ManagerFilter implements Filter {
    private static final Logger log = LogManager.getLogger(ManagerFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpSession session = ((HttpServletRequest) servletRequest).getSession(false);

        if(session != null && session.getAttribute("user") != null
                && ((User) session.getAttribute("user")).getRole() == Role.ROLE_MANAGER) {
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
