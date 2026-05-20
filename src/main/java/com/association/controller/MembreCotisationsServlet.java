package com.association.controller;

import com.association.model.CampagneCotisation;
import com.association.model.Membre;
import com.association.model.Utilisateur;
import com.association.service.CampagneCotisationService;
import com.association.service.CotisationService;
import com.association.service.MembreService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/membre/cotisations")
public class MembreCotisationsServlet extends HttpServlet {

    private final MembreService membreService = new MembreService();
    private final CampagneCotisationService campagneService = new CampagneCotisationService();
    private final CotisationService cotisationService = new CotisationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        Utilisateur utilisateur =
                (Utilisateur) session.getAttribute("utilisateurConnecte");

        Membre membre =
                membreService.rechercherParUtilisateurId(utilisateur.getId());

        List<CampagneCotisation> campagnes =
                campagneService.listerCampagnesActives();

        Map<Long, Boolean> paiements = new HashMap<>();

        for (CampagneCotisation campagne : campagnes) {
            boolean aPaye = cotisationService.membreAPayeCampagne(
                    membre.getId(),
                    campagne.getId()
            );

            paiements.put(campagne.getId(), aPaye);
        }

        request.setAttribute("membre", membre);
        request.setAttribute("campagnes", campagnes);
        request.setAttribute("paiements", paiements);

        request.getRequestDispatcher("/membre/cotisations.jsp")
                .forward(request, response);
    }
}