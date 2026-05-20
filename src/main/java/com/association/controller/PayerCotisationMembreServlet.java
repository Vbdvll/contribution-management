package com.association.controller;

import com.association.model.Membre;
import com.association.model.Utilisateur;
import com.association.service.CotisationService;
import com.association.service.MembreService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/membre/cotisations/payer")
public class PayerCotisationMembreServlet extends HttpServlet {

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

        Long campagneId =
                Long.parseLong(request.getParameter("campagneId"));

        try {
            cotisationService.declarerPaiementMembre(
                    membre.getId(),
                    campagneId,
                    "Déclaré par membre"
            );;

            response.sendRedirect(
                    request.getContextPath() + "/membre/cotisations"
            );

        } catch (Exception e) {
            response.sendRedirect(
                    request.getContextPath()
                            + "/membre/cotisations?erreur="
                            + java.net.URLEncoder.encode(e.getMessage(), "UTF-8")
            );
        }
    }
}