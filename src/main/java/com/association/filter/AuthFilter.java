package com.association.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebFilter({"/admin/*", "/membre/*"})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);

        boolean estConnecte = session != null
                && session.getAttribute("utilisateurConnecte") != null;

        if (!estConnecte) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }

        String role = (String) session.getAttribute("role");
        String uri = httpRequest.getRequestURI();

        if (uri.contains("/admin/") && !"ADMIN".equals(role)) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/membre/dashboard");
            return;
        }

        if (uri.contains("/membre/") && !"MEMBRE".equals(role)) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/admin/dashboard");
            return;
        }

        chain.doFilter(request, response);
    }
}