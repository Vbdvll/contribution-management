package com.association.controller;

import com.association.model.CampagneCotisation;
import com.association.model.Cotisation;
import com.association.model.Membre;
import com.association.service.CampagneCotisationService;
import com.association.service.CotisationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@WebServlet("/admin/cotisations")
public class ListeCotisationsServlet extends HttpServlet {

    private final CotisationService cotisationService =
            new CotisationService();

    private final CampagneCotisationService campagneService =
            new CampagneCotisationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<CampagneCotisation> campagnes =
                campagneService.listerCampagnes();

        request.setAttribute("campagnes", campagnes);

        Long campagneId = null;

        if (request.getParameter("campagneId") != null
                && !request.getParameter("campagneId").isEmpty()) {

            campagneId =
                    Long.parseLong(request.getParameter("campagneId"));
        }

        if (campagneId != null) {

            List<Cotisation> cotisations =
                    cotisationService.listerParCampagne(campagneId);

            boolean campagneEnRetard =
                    cotisationService.campagneEstEnRetard(campagneId);

            List<Membre> membresSansPaiement;

            if (campagneEnRetard) {
                membresSansPaiement =
                        cotisationService.membresEnRetardPourCampagne(campagneId);
            } else {
                membresSansPaiement =
                        cotisationService.membresSansPaiement(campagneId);
            }

            request.setAttribute("cotisations", cotisations);
            request.setAttribute("membresSansPaiement", membresSansPaiement);
            request.setAttribute("campagneId", campagneId);
            request.setAttribute("campagneEnRetard", campagneEnRetard);

        } else {

            request.setAttribute(
                    "cotisations",
                    cotisationService.listerToutesLesCotisations()
            );

            request.setAttribute(
                    "membresSansPaiement",
                    Collections.emptyList()
            );

            request.setAttribute("campagneEnRetard", false);
        }

        request.getRequestDispatcher("/admin/cotisations.jsp")
                .forward(request, response);
    }
}