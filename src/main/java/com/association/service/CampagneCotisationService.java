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
        CampagneCotisation campagne = new CampagneCotisation();

        campagne.setTitre(titre);
        campagne.setMontant(montant);

        campagne.setFrequence(
                CampagneCotisation.Frequence.valueOf(frequence)
        );

        campagne.setDateDebut(dateDebut);
        campagne.setDateFin(dateFin);
        campagne.setStatut(CampagneCotisation.Statut.ACTIVE);

        campagneDao.save(campagne);
    }

    public List<CampagneCotisation> listerCampagnes() {
        mettreAJourStatutsCampagnes();
        return campagneDao.findAll();
    }

    public List<CampagneCotisation> listerCampagnesActives() {
        mettreAJourStatutsCampagnes();
        return campagneDao.findActives();
    }

    public CampagneCotisation rechercherParId(Long id) {
        mettreAJourStatutsCampagnes();
        return campagneDao.findById(id);
    }

    public void mettreAJourStatutsCampagnes() {
        List<CampagneCotisation> campagnes = campagneDao.findAll();
        LocalDate aujourdHui = LocalDate.now();

        for (CampagneCotisation campagne : campagnes) {

            if (campagne.getDateFin() != null
                    && aujourdHui.isAfter(campagne.getDateFin())
                    && campagne.getStatut() == CampagneCotisation.Statut.ACTIVE) {

                campagne.setStatut(CampagneCotisation.Statut.TERMINEE);
                campagneDao.update(campagne);
            }
        }
    }

    public long compterCampagnesActives() {
        mettreAJourStatutsCampagnes();
        return campagneDao.countActives();
    }

    public BigDecimal montantTotalAttendu() {
        return campagneDao.montantTotalAttendu();
    }
}