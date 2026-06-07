package com.association.controller;

import com.association.model.Cotisation;
import com.association.service.CotisationService;
import com.association.service.NotificationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/admin/cotisations/valider")
public class ValiderPaiementServlet extends HttpServlet {

    private final CotisationService cotisationService =
            new CotisationService();

    private final NotificationService notificationService =
            new NotificationService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long cotisationId =
                Long.parseLong(request.getParameter("id"));

        String campagneId =
                request.getParameter("campagneId");

        cotisationService.validerPaiement(cotisationId);

        Cotisation cotisation =
                cotisationService.rechercherParId(cotisationId);

        if (cotisation != null
                && cotisation.getMembre() != null
                && cotisation.getMembre().getUtilisateur() != null) {

            notificationService.creerNotification(
                    cotisation.getMembre().getUtilisateur().getId(),
                    "Paiement validé",
                    "Votre paiement pour la campagne "
                            + cotisation.getCampagne().getTitre()
                            + " concernant l’échéance "
                            + cotisation.getDateEcheance()
                            + " a été validé.",
                    "VALIDATION_PAIEMENT"
            );
        }

        if (campagneId != null && !campagneId.isEmpty()) {
            response.sendRedirect(
                    request.getContextPath()
                            + "/admin/cotisations?campagneId="
                            + campagneId
            );
        } else {
            response.sendRedirect(
                    request.getContextPath() + "/admin/cotisations"
            );
        }
    }
}
