package com.association.controller;

import com.association.model.Membre;
import com.association.service.MembreService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.inject.Inject;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/admin/membres/modifier")
public class ModifierMembreServlet extends HttpServlet {

    @Inject
    private MembreService membreService;

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
        String email = request.getParameter("email");
        String statut = request.getParameter("statut");

        try {
            LocalDate dateNaissance =
                    LocalDate.parse(request.getParameter("dateNaissance"));
            LocalDate dateAdhesion =
                    LocalDate.parse(request.getParameter("dateAdhesion"));

            membreService.modifierMembre(
                    id,
                    numero,
                    prenom,
                    nom,
                    email,
                    dateNaissance,
                    dateAdhesion,
                    statut
            );
            response.sendRedirect(request.getContextPath() + "/admin/membres");
        } catch (Exception e) {
            request.setAttribute("erreur", e.getMessage());
            request.setAttribute("membre", membreService.rechercherParId(id));
            request.getRequestDispatcher("/admin/modifier-membre.jsp")
                    .forward(request, response);
        }
    }
}
