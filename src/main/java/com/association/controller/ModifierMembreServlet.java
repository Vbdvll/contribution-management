package com.association.controller;

import com.association.model.Membre;
import com.association.service.MembreService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/admin/membres/modifier")
public class ModifierMembreServlet extends HttpServlet {

    private final MembreService membreService = new MembreService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long id = Long.parseLong(request.getParameter("id"));

        Membre membre = membreService.rechercherParId(id);

        request.setAttribute("membre", membre);

        request.getRequestDispatcher("/admin/modifier-membre.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long id = Long.parseLong(request.getParameter("id"));
        String numero = request.getParameter("numero");
        String prenom = request.getParameter("prenom");
        String nom = request.getParameter("nom");
        String statut = request.getParameter("statut");

        membreService.modifierMembre(id, numero, prenom, nom, statut);

        response.sendRedirect(request.getContextPath() + "/admin/membres");
    }
}