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

        List<Cotisation> cotisations;

        if (campagneId != null) {
            cotisations =
                    cotisationService.listerParCampagne(campagneId);

            request.setAttribute("campagneId", campagneId);
            List<Membre> membresEnRetard =
                    cotisationService.membresEnRetard(campagneId);

            request.setAttribute("membresSansPaiement", membresEnRetard);
            request.setAttribute("campagneEnRetard", !membresEnRetard.isEmpty());

        } else {
            cotisations =
                    cotisationService.listerToutesLesCotisations();

            request.setAttribute(
                    "membresSansPaiement",
                    java.util.Collections.emptyList()
            );
            request.setAttribute("campagneEnRetard", false);
        }

        request.setAttribute("cotisations", cotisations);

        request.getRequestDispatcher("/admin/cotisations.jsp")
                .forward(request, response);
    }
}
