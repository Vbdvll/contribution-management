package com.association.filter;

import com.association.model.Utilisateur;
import com.association.service.MembreService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebFilter({"/admin/*", "/membre/*"})
public class AuthFilter implements Filter {

    private final MembreService membreService = new MembreService();

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

        if ("MEMBRE".equals(role)) {
            Utilisateur utilisateur =
                    (Utilisateur) session.getAttribute("utilisateurConnecte");

            if (membreService.rechercherParUtilisateurId(utilisateur.getId()) == null) {
                session.invalidate();
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
                return;
            }
        }

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
