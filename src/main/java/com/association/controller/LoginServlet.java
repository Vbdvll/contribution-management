package com.association.controller;

import com.association.model.Utilisateur;
import com.association.service.AuthService;
import com.association.service.HistoriqueConnexionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final AuthService authService = new AuthService();
    private final HistoriqueConnexionService historiqueService =
            new HistoriqueConnexionService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");

        Utilisateur utilisateur = authService.login(email, motDePasse);

        try {
            historiqueService.enregistrer(
                    email,
                    trouverAdresseIp(request),
                    utilisateur != null
            );
        } catch (Exception e) {
            getServletContext().log(
                    "Impossible d'enregistrer l'historique de connexion.",
                    e
            );
        }

        if (utilisateur == null) {
            request.setAttribute("erreur", "Email ou mot de passe incorrect.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        request.changeSessionId();
        session.setAttribute("csrfToken", UUID.randomUUID().toString());
        session.setAttribute("utilisateurConnecte", utilisateur);
        session.setAttribute("role", utilisateur.getRole().name());

        if (utilisateur.getRole() == Utilisateur.Role.ADMIN) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
        } else {
            response.sendRedirect(request.getContextPath() + "/membre/dashboard");
        }
    }

    private String trouverAdresseIp(HttpServletRequest request) {
        String adresseIp = request.getHeader("X-Forwarded-For");

        if (adresseIp != null && !adresseIp.isBlank()) {
            adresseIp = adresseIp.split(",")[0].trim();
        } else {
            adresseIp = request.getRemoteAddr();
        }

        if ("0:0:0:0:0:0:0:1".equals(adresseIp) || "::1".equals(adresseIp)) {
            return "127.0.0.1";
        }

        return adresseIp;
    }
}
