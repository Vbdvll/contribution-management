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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CotisationService {

    private final CotisationDao cotisationDao = new CotisationDao();
    private final MembreDao membreDao = new MembreDao();
    private final CampagneCotisationDao campagneDao = new CampagneCotisationDao();

    public void enregistrerCotisation(Long membreId, Long campagneId, String modePaiement) {
        enregistrerCotisation(membreId, campagneId, modePaiement, Cotisation.StatutCotisation.PAYEE);
    }

    public void declarerPaiementMembre(Long membreId, Long campagneId, String modePaiement) {
        enregistrerCotisation(membreId, campagneId, modePaiement, Cotisation.StatutCotisation.EN_ATTENTE);
    }

    private void enregistrerCotisation(
            Long membreId,
            Long campagneId,
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

        if (campagne.getStatut() != CampagneCotisation.Statut.ACTIVE) {
            throw new RuntimeException("Cette campagne n'est pas active.");
        }

        boolean dejaDeclare = cotisationDao.membreAPayeCampagne(membreId, campagneId);

        if (dejaDeclare) {
            throw new RuntimeException("Un paiement existe déjà pour cette campagne.");
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

    public boolean membreAPayeCampagne(Long membreId, Long campagneId) {
        return cotisationDao.membreAPayeCampagne(membreId, campagneId);
    }

    public List<Membre> membresSansPaiement(Long campagneId) {
        List<Membre> membres = membreDao.findAll();
        List<Membre> resultat = new ArrayList<>();

        for (Membre membre : membres) {
            boolean aDeclare = cotisationDao.membreAPayeCampagne(
                    membre.getId(),
                    campagneId
            );

            if (!aDeclare) {
                resultat.add(membre);
            }
        }

        return resultat;
    }

    public boolean campagneEstEnRetard(Long campagneId) {
        CampagneCotisation campagne = campagneDao.findById(campagneId);

        if (campagne == null) {
            return false;
        }

        if (campagne.getDateFin() == null) {
            return false;
        }

        return LocalDate.now().isAfter(campagne.getDateFin());
    }

    public List<Membre> membresEnRetardPourCampagne(Long campagneId) {
        if (!campagneEstEnRetard(campagneId)) {
            return new ArrayList<>();
        }

        return membresSansPaiement(campagneId);
    }

    public long compterMembresEnRetardGlobal() {
        List<CampagneCotisation> campagnes = campagneDao.findAll();
        Set<Long> membresEnRetardIds = new HashSet<>();

        for (CampagneCotisation campagne : campagnes) {
            if (campagne.getDateFin() != null
                    && LocalDate.now().isAfter(campagne.getDateFin())) {

                List<Membre> membresNonPayes =
                        membresSansPaiement(campagne.getId());

                for (Membre membre : membresNonPayes) {
                    membresEnRetardIds.add(membre.getId());
                }
            }
        }

        return membresEnRetardIds.size();
    }

    public long compterPaiementsEnAttente() {
        return cotisationDao.countPaiementsEnAttente();
    }

    public BigDecimal totalCotisations() {
        return cotisationDao.sommeTotaleCotisations();
    }
}