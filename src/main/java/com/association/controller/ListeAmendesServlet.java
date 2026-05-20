package com.association.controller;

import com.association.model.Amende;
import com.association.service.AmendeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/amendes")
public class ListeAmendesServlet extends HttpServlet {

    private final AmendeService amendeService = new AmendeService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Amende> amendes = amendeService.listerToutesLesAmendes();

        request.setAttribute("amendes", amendes);

        request.getRequestDispatcher("/admin/amendes.jsp")
                .forward(request, response);
    }
}