package com.association.controller;

import com.association.service.MembreService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.inject.Inject;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/admin/membres/ajouter")
public class AjouterMembreServlet extends HttpServlet {

    @Inject
    private MembreService membreService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("localDate", LocalDate.now());
        request.getRequestDispatcher("/admin/ajouter-membre.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String numero = request.getParameter("numero");
        String prenom = request.getParameter("prenom");
        String nom = request.getParameter("nom");
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");

        try {
            LocalDate dateNaissance =
                    LocalDate.parse(request.getParameter("dateNaissance"));
            LocalDate dateAdhesion =
                    LocalDate.parse(request.getParameter("dateAdhesion"));

            membreService.creerMembre(
                    numero,
                    prenom,
                    nom,
                    email,
                    motDePasse,
                    dateNaissance,
                    dateAdhesion
            );
            response.sendRedirect(request.getContextPath() + "/admin/membres");

        } catch (Exception e) {
            request.setAttribute("erreur", e.getMessage());
            request.getRequestDispatcher("/admin/ajouter-membre.jsp")
                    .forward(request, response);
        }
    }
}
