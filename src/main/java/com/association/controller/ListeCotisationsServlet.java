package com.association.controller;

import com.association.model.Cotisation;
import com.association.model.Membre;
import com.association.service.CotisationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/admin/cotisations")
public class ListeCotisationsServlet extends HttpServlet {

    private final CotisationService cotisationService = new CotisationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer mois = request.getParameter("mois") != null && !request.getParameter("mois").isEmpty()
                ? Integer.parseInt(request.getParameter("mois"))
                : LocalDate.now().getMonthValue();

        Integer annee = request.getParameter("annee") != null && !request.getParameter("annee").isEmpty()
                ? Integer.parseInt(request.getParameter("annee"))
                : LocalDate.now().getYear();

        String statut = request.getParameter("statut");

        List<Cotisation> cotisations =
                cotisationService.filtrerCotisations(mois, annee, statut);

        List<Membre> membresEnRetard =
                cotisationService.listerMembresEnRetard(mois, annee);

        request.setAttribute("cotisations", cotisations);
        request.setAttribute("membresEnRetard", membresEnRetard);
        request.setAttribute("mois", mois);
        request.setAttribute("annee", annee);
        request.setAttribute("statut", statut);

        request.getRequestDispatcher("/admin/cotisations.jsp")
                .forward(request, response);
    }
}