package com.association.controller;

import com.association.model.Membre;
import com.association.model.Utilisateur;
import com.association.service.CotisationService;
import com.association.service.MembreService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@WebServlet("/membre/cotisations/payer")
public class PayerCotisationMembreServlet extends HttpServlet {

    private final MembreService membreService = new MembreService();
    private final CotisationService cotisationService = new CotisationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HttpSession session = request.getSession(false);

            if (session == null || session.getAttribute("utilisateurConnecte") == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            Utilisateur utilisateur =
                    (Utilisateur) session.getAttribute("utilisateurConnecte");

            Membre membre =
                    membreService.rechercherParUtilisateurId(utilisateur.getId());

            if (membre == null) {
                throw new RuntimeException("Aucun membre associé à cet utilisateur.");
            }

            String campagneIdParam = request.getParameter("campagneId");
            String dateEcheanceParam = request.getParameter("dateEcheance");

            if (campagneIdParam == null || campagneIdParam.isEmpty()) {
                throw new RuntimeException("Campagne introuvable.");
            }

            if (dateEcheanceParam == null || dateEcheanceParam.isEmpty()) {
                throw new RuntimeException("Échéance introuvable.");
            }

            Long campagneId = Long.parseLong(campagneIdParam);
            LocalDate dateEcheance = LocalDate.parse(dateEcheanceParam);

            cotisationService.declarerPaiementMembre(
                    membre.getId(),
                    campagneId,
                    dateEcheance,
                    "Déclaré par membre"
            );

            String message = URLEncoder.encode(
                    "Paiement déclaré avec succès. En attente de validation admin.",
                    StandardCharsets.UTF_8
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/membre/cotisations?success="
                            + message
            );

        } catch (Exception e) {

            e.printStackTrace();

            String erreur = URLEncoder.encode(
                    e.getMessage(),
                    StandardCharsets.UTF_8
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/membre/cotisations?erreur="
                            + erreur
            );
        }
    }
}