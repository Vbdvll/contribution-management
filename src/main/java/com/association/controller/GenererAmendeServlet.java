package com.association.controller;

import com.association.model.CampagneCotisation;
import com.association.model.Membre;
import com.association.service.AmendeService;
import com.association.service.CampagneCotisationService;
import com.association.service.MembreService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/admin/amendes/generer")
public class GenererAmendeServlet extends HttpServlet {

    private final AmendeService amendeService = new AmendeService();
    private final MembreService membreService = new MembreService();
    private final CampagneCotisationService campagneService =
            new CampagneCotisationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Membre> membres = membreService.listerTousLesMembres();
        List<CampagneCotisation> campagnes = campagneService.listerCampagnes();

        request.setAttribute("membres", membres);
        request.setAttribute("campagnes", campagnes);

        request.setAttribute("membreId", request.getParameter("membreId"));
        request.setAttribute("campagneId", request.getParameter("campagneId"));

        request.getRequestDispatcher("/admin/generer-amende.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Long membreId = Long.parseLong(request.getParameter("membreId"));
            Long campagneId = Long.parseLong(request.getParameter("campagneId"));
            BigDecimal montant = new BigDecimal(request.getParameter("montant"));

            amendeService.genererAmende(membreId, campagneId, montant);
            response.sendRedirect(request.getContextPath() + "/admin/amendes");
        } catch (Exception e) {
            request.setAttribute("erreur", e.getMessage());
            doGet(request, response);
        }
    }
}
