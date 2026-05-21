package com.association.service;

import com.association.dao.CampagneCotisationDao;
import com.association.dao.CotisationDao;
import com.association.dao.MembreDao;
import com.association.model.CampagneCotisation;
import com.association.model.Cotisation;
import com.association.model.Membre;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CotisationService {

    private final CotisationDao cotisationDao = new CotisationDao();
    private final MembreDao membreDao = new MembreDao();
    private final CampagneCotisationDao campagneDao = new CampagneCotisationDao();

    public void enregistrerCotisation(
            Long membreId,
            Long campagneId,
            LocalDate dateEcheance,
            String modePaiement
    ) {
        enregistrerCotisation(
                membreId,
                campagneId,
                dateEcheance,
                modePaiement,
                Cotisation.StatutCotisation.PAYEE
        );
    }

    public void declarerPaiementMembre(
            Long membreId,
            Long campagneId,
            LocalDate dateEcheance,
            String modePaiement
    ) {
        enregistrerCotisation(
                membreId,
                campagneId,
                dateEcheance,
                modePaiement,
                Cotisation.StatutCotisation.EN_ATTENTE
        );
    }

    private void enregistrerCotisation(
            Long membreId,
            Long campagneId,
            LocalDate dateEcheance,
            String modePaiement,
            Cotisation.StatutCotisation statut
    ) {
        Membre membre = membreDao.findById(membreId);

        if (membre == null) {
            throw new RuntimeException("Membre introuvable.");
        }

        CampagneCotisation campagne = campagneDao.findById(campagneId);

        if (campagne == null) {
            throw new RuntimeException("Campagne introuvable.");
        }

        if (dateEcheance == null) {
            throw new RuntimeException("Échéance invalide.");
        }

        LocalDate aujourdHui = LocalDate.now();

        if (dateEcheance.isAfter(aujourdHui)) {
            throw new RuntimeException("Cette échéance n'est pas encore disponible.");
        }

        if (dateEcheance.isBefore(campagne.getDateDebut())) {
            throw new RuntimeException("Cette échéance est avant le début de la campagne.");
        }

        if (campagne.getDateFin() != null && dateEcheance.isAfter(campagne.getDateFin())) {
            throw new RuntimeException("Cette échéance dépasse la date de fin de la campagne.");
        }

        if (dateEcheance.isBefore(aujourdHui) && !campagne.isRetardTolere()) {
            throw new RuntimeException("Le paiement en retard n'est pas autorisé pour cette campagne.");
        }

        boolean dejaDeclare = cotisationDao.membreAPayeEcheance(
                membreId,
                campagneId,
                dateEcheance
        );

        if (dejaDeclare) {
            throw new RuntimeException("Un paiement existe déjà pour cette échéance.");
        }

        Cotisation cotisation = new Cotisation();
        cotisation.setMembre(membre);
        cotisation.setCampagne(campagne);
        cotisation.setDateEcheance(dateEcheance);
        cotisation.setMontant(campagne.getMontant());
        cotisation.setMois(dateEcheance.getMonthValue());
        cotisation.setAnnee(dateEcheance.getYear());
        cotisation.setDatePaiement(aujourdHui);
        cotisation.setModePaiement(modePaiement);
        cotisation.setStatut(statut);

        cotisationDao.save(cotisation);
    }

    public void validerPaiement(Long cotisationId) {
        Cotisation cotisation = cotisationDao.findById(cotisationId);

        if (cotisation == null) {
            throw new RuntimeException("Paiement introuvable.");
        }

        cotisation.setStatut(Cotisation.StatutCotisation.PAYEE);
        cotisationDao.update(cotisation);
    }

    public List<Cotisation> listerToutesLesCotisations() {
        return cotisationDao.findAll();
    }

    public List<Cotisation> listerParCampagne(Long campagneId) {
        return cotisationDao.findByCampagne(campagneId);
    }

    public List<Cotisation> listerPaiementsParMembre(Long membreId) {
        return cotisationDao.findByMembre(membreId);
    }

    public Cotisation rechercherPaiementMembreCampagneEcheance(
            Long membreId,
            Long campagneId,
            LocalDate dateEcheance
    ) {
        return cotisationDao.findByMembreCampagneEtEcheance(
                membreId,
                campagneId,
                dateEcheance
        );
    }

    public List<Membre> membresSansPaiement(
            Long campagneId,
            LocalDate dateEcheance
    ) {
        List<Membre> membres = membreDao.findAll();
        List<Membre> resultat = new ArrayList<>();

        for (Membre membre : membres) {
            boolean aDeclare = cotisationDao.membreAPayeEcheance(
                    membre.getId(),
                    campagneId,
                    dateEcheance
            );

            if (!aDeclare) {
                resultat.add(membre);
            }
        }

        return resultat;
    }

    public long compterPaiementsEnAttente() {
        return cotisationDao.countPaiementsEnAttente();
    }

    public BigDecimal totalCotisations() {
        return cotisationDao.sommeTotaleCotisations();
    }
}