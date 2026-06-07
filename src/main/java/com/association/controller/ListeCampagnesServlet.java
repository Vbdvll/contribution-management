package com.association.controller;

import com.association.model.CampagneCotisation;
import com.association.service.CampagneCotisationService;
import com.association.service.ParticipationCampagneService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet("/admin/campagnes")
public class ListeCampagnesServlet extends HttpServlet {

    private final CampagneCotisationService campagneService =
            new CampagneCotisationService();
    private final ParticipationCampagneService participationService =
            new ParticipationCampagneService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<CampagneCotisation> campagnes =
                campagneService.listerCampagnes();

        request.setAttribute("campagnes", campagnes);

        Map<Long, Integer> nombreParticipants = new LinkedHashMap<>();
        for (CampagneCotisation campagne : campagnes) {
            nombreParticipants.put(
                    campagne.getId(),
                    participationService.compterParticipants(campagne)
            );
        }
        request.setAttribute("nombreParticipants", nombreParticipants);

        request.getRequestDispatcher("/admin/campagnes.jsp")
                .forward(request, response);
    }
}
