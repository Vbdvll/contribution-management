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
            String modePaiement
    ) {
        Membre membre = membreDao.findById(membreId);

        if (membre == null) {
            throw new RuntimeException("Membre introuvable.");
        }

        CampagneCotisation campagne = campagneDao.findById(campagneId);

        if (campagne == null) {
            throw new RuntimeException("Campagne introuvable.");
        }

        if (campagne.getStatut() != CampagneCotisation.Statut.ACTIVE) {
            throw new RuntimeException("Cette campagne n'est pas active.");
        }

        LocalDate aujourdHui = LocalDate.now();

        Cotisation cotisation = new Cotisation();
        cotisation.setMembre(membre);
        cotisation.setCampagne(campagne);
        cotisation.setMontant(campagne.getMontant());
        cotisation.setMois(aujourdHui.getMonthValue());
        cotisation.setAnnee(aujourdHui.getYear());
        cotisation.setDatePaiement(aujourdHui);
        cotisation.setModePaiement(modePaiement);
        cotisation.setStatut(Cotisation.StatutCotisation.PAYEE);

        cotisationDao.save(cotisation);
    }

    public List<Cotisation> listerToutesLesCotisations() {
        return cotisationDao.findAll();
    }

    public BigDecimal totalCotisations() {
        return cotisationDao.sommeTotaleCotisations();
    }

    public List<Cotisation> filtrerCotisations(
            Integer mois,
            Integer annee,
            String statut
    ) {
        return cotisationDao.filtrer(mois, annee, statut);
    }

    public List<Membre> listerMembresEnRetard(
            Integer mois,
            Integer annee
    ) {
        List<Membre> tousLesMembres = membreDao.findAll();
        List<Membre> membresEnRetard = new ArrayList<>();

        for (Membre membre : tousLesMembres) {
            boolean aPaye =
                    cotisationDao.existeCotisationPourMembreEtPeriode(
                            membre.getId(),
                            mois,
                            annee
                    );

            if (!aPaye) {
                membresEnRetard.add(membre);
            }
        }

        return membresEnRetard;
    }
    public boolean membreAPayeCampagne(Long membreId, Long campagneId) {
        return cotisationDao.membreAPayeCampagne(membreId, campagneId);
    }
}