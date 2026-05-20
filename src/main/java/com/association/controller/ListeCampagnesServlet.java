package com.association.controller;

import com.association.model.CampagneCotisation;
import com.association.service.CampagneCotisationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/campagnes")
public class ListeCampagnesServlet extends HttpServlet {

    private final CampagneCotisationService campagneService =
            new CampagneCotisationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<CampagneCotisation> campagnes =
                campagneService.listerCampagnes();

        request.setAttribute("campagnes", campagnes);

        request.getRequestDispatcher("/admin/campagnes.jsp")
                .forward(request, response);
    }
}