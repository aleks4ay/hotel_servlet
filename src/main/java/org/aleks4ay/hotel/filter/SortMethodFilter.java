package org.aleks4ay.hotel.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter( urlPatterns = { "/*" })
public class SortMethodFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String sortRequest = request.getParameter("sortMethod");
        Object sortSession = request.getSession().getAttribute("sortMethod");
        if (sortRequest != null) {
            request.getSession().setAttribute("sortMethod", sortRequest);
            request.setAttribute("sortMethod", sortRequest);
        } else {
            request.getSession().setAttribute("sortMethod", sortSession == null ? "number" : sortSession);
            request.setAttribute("sortMethod", request.getSession().getAttribute("sortMethod"));
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
