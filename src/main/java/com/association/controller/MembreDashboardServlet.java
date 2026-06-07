package com.association.controller;

import com.association.model.Membre;
import com.association.model.Cotisation;
import com.association.model.Amende;
import com.association.model.Utilisateur;
import com.association.service.AmendeService;
import com.association.service.CotisationService;
import com.association.service.MembreService;
import com.association.service.NotificationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/membre/dashboard")
public class MembreDashboardServlet extends HttpServlet {

    private final MembreService membreService =
            new MembreService();

    private final NotificationService notificationService =
            new NotificationService();

    private final CotisationService cotisationService =
            new CotisationService();

    private final AmendeService amendeService =
            new AmendeService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        if (session == null || session.getAttribute("utilisateurConnecte") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Utilisateur utilisateur =
                (Utilisateur) session.getAttribute("utilisateurConnecte");

        Membre membre =
                membreService.rechercherParUtilisateurId(
                        utilisateur.getId()
                );

        long notificationsNonLues =
                notificationService.compterNotificationsNonLues(
                        utilisateur.getId()
                );

        List<Cotisation> paiements =
                cotisationService.listerPaiementsParMembre(membre.getId());

        List<Amende> amendes =
                amendeService.listerAmendesParMembre(membre.getId());

        long paiementsValides = 0;
        long paiementsEnAttente = 0;
        long amendesNonPayees = 0;

        for (Cotisation paiement : paiements) {
            if (paiement.getStatut() == Cotisation.StatutCotisation.PAYEE) {
                paiementsValides++;
            } else if (paiement.getStatut() == Cotisation.StatutCotisation.EN_ATTENTE) {
                paiementsEnAttente++;
            }
        }

        for (Amende amende : amendes) {
            if (amende.getStatutPaiement() == Amende.StatutPaiement.NON_PAYEE) {
                amendesNonPayees++;
            }
        }

        request.setAttribute("membre", membre);
        request.setAttribute("notificationsNonLues", notificationsNonLues);
        request.setAttribute("paiementsValides", paiementsValides);
        request.setAttribute("paiementsEnAttente", paiementsEnAttente);
        request.setAttribute("amendesNonPayees", amendesNonPayees);

        request.setAttribute(
                "notificationsRecentes",
                notificationService.listerNotificationsRecentes(
                        utilisateur.getId()
                )
        );

        request.getRequestDispatcher("/membre/dashboard.jsp")
                .forward(request, response);
    }
}
