package com.association.controller;

import com.association.service.AmendeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/admin/amendes/payer")
public class PayerAmendeServlet extends HttpServlet {

    private final AmendeService amendeService = new AmendeService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long id = Long.parseLong(request.getParameter("id"));

        amendeService.marquerCommePayee(id);

        response.sendRedirect(request.getContextPath() + "/admin/amendes");
    }
}