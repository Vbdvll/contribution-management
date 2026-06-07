package com.association.controller;

import com.association.service.CampagneCotisationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

@WebServlet("/admin/campagnes/ajouter")
public class AjouterCampagneServlet extends HttpServlet {

    private final CampagneCotisationService campagneService =
            new CampagneCotisationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/admin/ajouter-campagne.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String titre = request.getParameter("titre");
            BigDecimal montant = new BigDecimal(request.getParameter("montant"));
            String frequence = request.getParameter("frequence");

            LocalDate dateDebut =
                    LocalDate.parse(request.getParameter("dateDebut"));

            String dateFinParam = request.getParameter("dateFin");
            LocalDate dateFin = null;

            if (dateFinParam != null && !dateFinParam.isEmpty()) {
                dateFin = LocalDate.parse(dateFinParam);
            }

            boolean retardTolere =
                    request.getParameter("retardTolere") != null;
            String typeParticipation =
                    request.getParameter("typeParticipation");

            campagneService.creerCampagne(
                    titre,
                    montant,
                    frequence,
                    dateDebut,
                    dateFin,
                    retardTolere,
                    typeParticipation
            );

            response.sendRedirect(request.getContextPath() + "/admin/campagnes");
        } catch (Exception e) {
            request.setAttribute("erreur", e.getMessage());
            request.getRequestDispatcher("/admin/ajouter-campagne.jsp")
                    .forward(request, response);
        }
    }
}
