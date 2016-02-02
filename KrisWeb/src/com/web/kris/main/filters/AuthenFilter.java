package com.web.kris.main.filters;

import com.web.kris.main.entities.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Mohru on 2016-02-02.
 */
public class AuthenFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


        HttpSession session = ((HttpServletRequest) servletRequest).getSession(false);
        User user = (User)((session != null) ? session.getAttribute("user") : null);

        String path = ((HttpServletRequest) servletRequest).getRequestURI();
        boolean isExcludedPath = path.equals("/") || path.startsWith("/index") || path.startsWith("/login") || path.startsWith("/register")
                  ||  path.equals("/styles.css") || path.equals("/logo.png") || path.equals("/RegisterServlet") || path.equals("/LoginServlet");

        try {
            if (!isExcludedPath && (user == null || !user.getIsActive())) {

                ((HttpServletResponse) servletResponse).sendRedirect("/");
            } else
                filterChain.doFilter(servletRequest, servletResponse);

        } catch(Exception e) {
            System.out.println(e.getMessage());

        }
    }

    @Override
    public void destroy() {

    }
}
