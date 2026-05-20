package com.association.controller;

import com.association.service.MembreService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/admin/membres/ajouter")
public class AjouterMembreServlet extends HttpServlet {

    private final MembreService membreService = new MembreService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
            membreService.creerMembre(numero, prenom, nom, email, motDePasse);
            response.sendRedirect(request.getContextPath() + "/admin/membres");

        } catch (Exception e) {
            request.setAttribute("erreur", e.getMessage());
            request.getRequestDispatcher("/admin/ajouter-membre.jsp")
                    .forward(request, response);
        }
    }
}