package com.association.controller;

import com.association.model.Membre;
import com.association.service.AmendeService;
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Membre> membres = membreService.listerTousLesMembres();
        request.setAttribute("membres", membres);

        request.getRequestDispatcher("/admin/generer-amende.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long membreId = Long.parseLong(request.getParameter("membreId"));
        BigDecimal montant = new BigDecimal(request.getParameter("montant"));

        amendeService.genererAmende(membreId, montant);

        response.sendRedirect(request.getContextPath() + "/admin/amendes");
    }
}