package com.association.controller;

import com.association.model.Utilisateur;
import com.association.service.AmendeService;
import com.association.service.CampagneCotisationService;
import com.association.service.CotisationService;
import com.association.service.MembreService;
import com.association.service.NotificationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {

    private final MembreService membreService =
            new MembreService();

    private final CotisationService cotisationService =
            new CotisationService();

    private final AmendeService amendeService =
            new AmendeService();

    private final NotificationService notificationService =
            new NotificationService();

    private final CampagneCotisationService campagneService =
            new CampagneCotisationService();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    )
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        Utilisateur utilisateur =
                (Utilisateur) session.getAttribute("utilisateurConnecte");

        long totalMembres =
                membreService.compterMembres();

        BigDecimal totalCotisations =
                cotisationService.totalCotisations();

        BigDecimal totalAmendesPayees =
                amendeService.totalAmendesPayees();

        BigDecimal totalAmendesNonPayees =
                amendeService.totalAmendesNonPayees();

        long paiementsEnAttente =
                cotisationService.compterPaiementsEnAttente();

        int campagnesActives =
                campagneService.listerCampagnesActives().size();

        long notificationsNonLues =
                notificationService.compterNotificationsNonLues(
                        utilisateur.getId()
                );


        request.setAttribute("totalMembres", totalMembres);
        request.setAttribute("totalCotisations", totalCotisations);
        request.setAttribute("totalAmendesPayees", totalAmendesPayees);
        request.setAttribute("totalAmendesNonPayees", totalAmendesNonPayees);
        request.setAttribute("paiementsEnAttente", paiementsEnAttente);
        request.setAttribute("campagnesActives", campagnesActives);
        request.setAttribute("notificationsNonLues", notificationsNonLues);
        request.setAttribute(
                "notificationsRecentes",
                notificationService.listerNotificationsRecentes(
                        utilisateur.getId()
                )
        );

        request.getRequestDispatcher("/admin/dashboard.jsp")
                .forward(request, response);
    }
}
