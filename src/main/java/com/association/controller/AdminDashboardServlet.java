package com.association.controller;

import com.association.service.AmendeService;
import com.association.service.CotisationService;
import com.association.service.MembreService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {

    private final MembreService membreService = new MembreService();
    private final CotisationService cotisationService = new CotisationService();
    private final AmendeService amendeService = new AmendeService();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        long totalMembres = membreService.compterMembres();

        BigDecimal totalCotisations =
                cotisationService.totalCotisations();

        BigDecimal totalAmendesPayees =
                amendeService.totalAmendesPayees();

        BigDecimal totalAmendesNonPayees =
                amendeService.totalAmendesNonPayees();

        request.setAttribute(
                "totalMembres",
                totalMembres
        );

        request.setAttribute(
                "totalCotisations",
                totalCotisations
        );

        request.setAttribute(
                "totalAmendesPayees",
                totalAmendesPayees
        );

        request.setAttribute(
                "totalAmendesNonPayees",
                totalAmendesNonPayees
        );

        request.getRequestDispatcher(
                "/admin/dashboard.jsp"
        ).forward(request, response);
    }
}