package com.association.controller;

import com.association.model.Membre;
import com.association.model.Utilisateur;
import com.association.service.CotisationService;
import com.association.service.MembreService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@WebServlet("/membre/cotisations/payer")
public class PayerCotisationMembreServlet extends HttpServlet {

    private final MembreService membreService = new MembreService();
    private final CotisationService cotisationService = new CotisationService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            HttpSession session = request.getSession(false);
            Utilisateur utilisateur =
                    (Utilisateur) session.getAttribute("utilisateurConnecte");
            Membre membre =
                    membreService.rechercherParUtilisateurId(utilisateur.getId());

            Long campagneId =
                    Long.parseLong(request.getParameter("campagneId"));
            LocalDate dateEcheance =
                    LocalDate.parse(request.getParameter("dateEcheance"));

            cotisationService.payerCotisationMembre(
                    membre.getId(),
                    campagneId,
                    dateEcheance
            );

            rediriger(
                    request,
                    response,
                    "success",
                    "Paiement effectué et confirmé avec succès"
            );
        } catch (Exception e) {
            rediriger(request, response, "erreur", e.getMessage());
        }
    }

    private void rediriger(
            HttpServletRequest request,
            HttpServletResponse response,
            String parametre,
            String message
    ) throws IOException {
        response.sendRedirect(
                request.getContextPath()
                        + "/membre/cotisations?"
                        + parametre
                        + "="
                        + URLEncoder.encode(message, StandardCharsets.UTF_8)
        );
    }
}
