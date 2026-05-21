package com.association.controller;

import com.association.model.Cotisation;
import com.association.model.Membre;
import com.association.model.Utilisateur;
import com.association.service.CotisationService;
import com.association.service.MembreService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/membre/historique")
public class MembreHistoriquePaiementsServlet extends HttpServlet {

    private final MembreService membreService = new MembreService();
    private final CotisationService cotisationService = new CotisationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        Utilisateur utilisateur =
                (Utilisateur) session.getAttribute("utilisateurConnecte");

        Membre membre =
                membreService.rechercherParUtilisateurId(utilisateur.getId());

        List<Cotisation> paiements =
                cotisationService.listerPaiementsParMembre(membre.getId());

        request.setAttribute("membre", membre);
        request.setAttribute("paiements", paiements);

        request.getRequestDispatcher("/membre/historique.jsp")
                .forward(request, response);
    }
}