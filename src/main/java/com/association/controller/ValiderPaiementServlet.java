package com.association.controller;

import com.association.service.CotisationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/admin/cotisations/valider")
public class ValiderPaiementServlet extends HttpServlet {

    private final CotisationService cotisationService =
            new CotisationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long cotisationId =
                Long.parseLong(request.getParameter("id"));

        String campagneId =
                request.getParameter("campagneId");

        cotisationService.validerPaiement(cotisationId);

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