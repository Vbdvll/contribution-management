package com.association.controller;

import com.association.model.Membre;
import com.association.model.Utilisateur;
import com.association.service.MembreService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/membre/dashboard")
public class MembreDashboardServlet extends HttpServlet {

    private final MembreService membreService = new MembreService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        Utilisateur utilisateur =
                (Utilisateur) session.getAttribute("utilisateurConnecte");

        Membre membre =
                membreService.rechercherParUtilisateurId(utilisateur.getId());

        request.setAttribute("membre", membre);

        request.getRequestDispatcher("/membre/dashboard.jsp")
                .forward(request, response);
    }
}