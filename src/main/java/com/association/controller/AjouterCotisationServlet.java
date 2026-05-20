package com.association.controller;

import com.association.model.CampagneCotisation;
import com.association.model.Membre;
import com.association.service.CampagneCotisationService;
import com.association.service.CotisationService;
import com.association.service.MembreService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/cotisations/ajouter")
public class AjouterCotisationServlet extends HttpServlet {

    private final CotisationService cotisationService =
            new CotisationService();

    private final MembreService membreService =
            new MembreService();

    private final CampagneCotisationService campagneService =
            new CampagneCotisationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Membre> membres =
                membreService.listerTousLesMembres();

        List<CampagneCotisation> campagnes =
                campagneService.listerCampagnes();

        request.setAttribute("membres", membres);
        request.setAttribute("campagnes", campagnes);

        request.getRequestDispatcher("/admin/ajouter-cotisation.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long membreId =
                Long.parseLong(request.getParameter("membreId"));

        Long campagneId =
                Long.parseLong(request.getParameter("campagneId"));

        String modePaiement =
                request.getParameter("modePaiement");

        try {
            cotisationService.enregistrerCotisation(
                    membreId,
                    campagneId,
                    modePaiement
            );

            response.sendRedirect(
                    request.getContextPath() + "/admin/cotisations"
            );

        } catch (Exception e) {

            request.setAttribute("erreur", e.getMessage());

            List<Membre> membres =
                    membreService.listerTousLesMembres();

            List<CampagneCotisation> campagnes =
                    campagneService.listerCampagnes();

            request.setAttribute("membres", membres);
            request.setAttribute("campagnes", campagnes);

            request.getRequestDispatcher("/admin/ajouter-cotisation.jsp")
                    .forward(request, response);
        }
    }
}