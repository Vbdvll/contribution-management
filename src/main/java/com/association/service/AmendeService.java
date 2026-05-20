package com.association.service;

import com.association.dao.AmendeDao;
import com.association.dao.CampagneCotisationDao;
import com.association.dao.MembreDao;
import com.association.model.Amende;
import com.association.model.CampagneCotisation;
import com.association.model.Membre;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class AmendeService {

    private final AmendeDao amendeDao = new AmendeDao();
    private final MembreDao membreDao = new MembreDao();
    private final CampagneCotisationDao campagneDao = new CampagneCotisationDao();

    public void genererAmende(Long membreId, Long campagneId, BigDecimal montant) {

        boolean existe = amendeDao.existeAmende(membreId, campagneId);

        if (existe) {
            throw new RuntimeException("Une amende existe déjà pour cette campagne.");
        }

        Membre membre = membreDao.findById(membreId);

        if (membre == null) {
            throw new RuntimeException("Membre introuvable.");
        }

        CampagneCotisation campagne = campagneDao.findById(campagneId);

        if (campagne == null) {
            throw new RuntimeException("Campagne introuvable.");
        }

        Amende amende = new Amende();
        amende.setMembre(membre);
        amende.setCampagne(campagne);
        amende.setMontant(montant);
        amende.setDateGeneration(LocalDate.now());
        amende.setStatutPaiement(Amende.StatutPaiement.NON_PAYEE);

        amendeDao.save(amende);
    }

    public List<Amende> listerToutesLesAmendes() {
        return amendeDao.findAll();
    }

    public void marquerCommePayee(Long id) {
        Amende amende = amendeDao.findById(id);

        if (amende == null) {
            throw new RuntimeException("Amende introuvable.");
        }

        amende.setStatutPaiement(Amende.StatutPaiement.PAYEE);
        amendeDao.update(amende);
    }

    public BigDecimal totalAmendesPayees() {
        return amendeDao.sommeAmendesPayees();
    }

    public BigDecimal totalAmendesNonPayees() {
        return amendeDao.sommeAmendesNonPayees();
    }
}