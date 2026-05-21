package com.association.controller;

import com.association.model.Amende;
import com.association.model.Membre;
import com.association.model.Utilisateur;
import com.association.service.AmendeService;
import com.association.service.MembreService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/membre/amendes")
public class MembreAmendesServlet extends HttpServlet {

    private final MembreService membreService = new MembreService();
    private final AmendeService amendeService = new AmendeService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        Utilisateur utilisateur =
                (Utilisateur) session.getAttribute("utilisateurConnecte");

        Membre membre =
                membreService.rechercherParUtilisateurId(utilisateur.getId());

        List<Amende> amendes =
                amendeService.listerAmendesParMembre(membre.getId());

        request.setAttribute("membre", membre);
        request.setAttribute("amendes", amendes);

        request.getRequestDispatcher("/membre/amendes.jsp")
                .forward(request, response);
    }
}