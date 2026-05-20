package com.association.service;

import com.association.dao.CampagneCotisationDao;
import com.association.model.CampagneCotisation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CampagneCotisationService {

    private final CampagneCotisationDao campagneDao =
            new CampagneCotisationDao();

    public void creerCampagne(
            String titre,
            BigDecimal montant,
            String frequence,
            LocalDate dateDebut,
            LocalDate dateFin
    ) {
        CampagneCotisation campagne =
                new CampagneCotisation();

        campagne.setTitre(titre);
        campagne.setMontant(montant);

        campagne.setFrequence(
                CampagneCotisation.Frequence.valueOf(frequence)
        );

        campagne.setDateDebut(dateDebut);
        campagne.setDateFin(dateFin);

        campagne.setStatut(
                CampagneCotisation.Statut.ACTIVE
        );

        campagneDao.save(campagne);
    }

    public List<CampagneCotisation> listerCampagnes() {
        return campagneDao.findAll();
    }

    public List<CampagneCotisation> listerCampagnesActives() {
        return campagneDao.findActives();
    }
}