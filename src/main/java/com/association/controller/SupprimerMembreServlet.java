package com.association.controller;

import com.association.service.MembreService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/admin/membres/supprimer")
public class SupprimerMembreServlet extends HttpServlet {

    private final MembreService membreService = new MembreService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long id = Long.parseLong(request.getParameter("id"));

        membreService.supprimerMembre(id);

        response.sendRedirect(request.getContextPath() + "/admin/membres");
    }
}