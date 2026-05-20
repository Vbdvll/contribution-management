package com.association.controller;

import com.association.model.Membre;
import com.association.service.MembreService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/membres")
public class ListeMembresServlet extends HttpServlet {

    private final MembreService membreService = new MembreService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String recherche = request.getParameter("recherche");

        List<Membre> membres = membreService.rechercherMembres(recherche);

        request.setAttribute("membres", membres);
        request.setAttribute("recherche", recherche);

        request.getRequestDispatcher("/admin/membres.jsp")
                .forward(request, response);
    }
}