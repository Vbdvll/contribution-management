package com.association.controller;

import com.association.model.CampagneCotisation;
import com.association.model.Cotisation;
import com.association.model.Membre;
import com.association.model.Utilisateur;
import com.association.service.CampagneCotisationService;
import com.association.service.CotisationService;
import com.association.service.MembreService;
import com.association.service.ParticipationCampagneService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/membre/cotisations")
public class MembreCotisationsServlet extends HttpServlet {

    private final MembreService membreService = new MembreService();
    private final CampagneCotisationService campagneService = new CampagneCotisationService();
    private final CotisationService cotisationService = new CotisationService();
    private final ParticipationCampagneService participationService =
            new ParticipationCampagneService();

    public static class LigneCotisation {
        private CampagneCotisation campagne;
        private LocalDate dateEcheance;
        private String statut;

        public LigneCotisation(
                CampagneCotisation campagne,
                LocalDate dateEcheance,
                String statut
        ) {
            this.campagne = campagne;
            this.dateEcheance = dateEcheance;
            this.statut = statut;
        }

        public CampagneCotisation getCampagne() {
            return campagne;
        }

        public LocalDate getDateEcheance() {
            return dateEcheance;
        }

        public String getStatut() {
            return statut;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        Utilisateur utilisateur =
                (Utilisateur) session.getAttribute("utilisateurConnecte");

        Membre membre =
                membreService.rechercherParUtilisateurId(utilisateur.getId());

        List<CampagneCotisation> campagnes =
                participationService.listerCampagnesPourMembre(
                        membre.getId(),
                        campagneService.listerCampagnes()
                );

        List<LigneCotisation> lignes = new ArrayList<>();

        for (CampagneCotisation campagne : campagnes) {

            List<LocalDate> echeances =
                    genererEcheances(campagne);

            for (LocalDate dateEcheance : echeances) {

                Cotisation paiement =
                        cotisationService.rechercherPaiementMembreCampagneEcheance(
                                membre.getId(),
                                campagne.getId(),
                                dateEcheance
                        );

                String statut;

                if (paiement == null) {

                    if (LocalDate.now().isAfter(dateEcheance)) {
                        statut = "EN_RETARD";
                    } else {
                        statut = "NON_PAYEE";
                    }

                } else if (paiement.getStatut() == Cotisation.StatutCotisation.EN_ATTENTE) {

                    statut = "EN_ATTENTE";

                } else if (paiement.getStatut() == Cotisation.StatutCotisation.PAYEE) {

                    statut = "PAYEE";

                } else {
                    statut = "EN_RETARD";
                }

                lignes.add(
                        new LigneCotisation(
                                campagne,
                                dateEcheance,
                                statut
                        )
                );
            }
        }

        request.setAttribute("membre", membre);
        request.setAttribute("lignes", lignes);

        request.getRequestDispatcher("/membre/cotisations.jsp")
                .forward(request, response);
    }

    private List<LocalDate> genererEcheances(CampagneCotisation campagne) {

        List<LocalDate> echeances = new ArrayList<>();

        LocalDate debut = campagne.getDateDebut();
        LocalDate fin = campagne.getDateFin();

        if (debut == null) {
            return echeances;
        }

        if (fin == null) {
            fin = LocalDate.now();
        }

        LocalDate current = debut;

        while (!current.isAfter(fin)) {

            echeances.add(current);

            switch (campagne.getFrequence()) {

                case JOURNALIER:
                    current = current.plusDays(1);
                    break;

                case HEBDOMADAIRE:
                    current = current.plusWeeks(1);
                    break;

                case MENSUEL:
                    current = current.plusMonths(1);
                    break;
            }
        }

        return echeances;
    }
}
