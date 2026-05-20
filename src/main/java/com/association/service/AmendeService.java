package com.association.service;

import com.association.dao.AmendeDao;
import com.association.dao.MembreDao;
import com.association.model.Amende;
import com.association.model.Membre;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class AmendeService {

    private final AmendeDao amendeDao = new AmendeDao();
    private final MembreDao membreDao = new MembreDao();

    public void genererAmende(Long membreId, BigDecimal montant) {

        Membre membre = membreDao.findById(membreId);

        if (membre == null) {
            throw new RuntimeException("Membre introuvable.");
        }

        Amende amende = new Amende();
        amende.setMembre(membre);
        amende.setMontant(montant);
        amende.setDateGeneration(LocalDate.now());
        amende.setStatutPaiement(Amende.StatutPaiement.NON_PAYEE);

        amendeDao.save(amende);
    }
    public void marquerCommePayee(Long id) {
        Amende amende = amendeDao.findById(id);

        if (amende == null) {
            throw new RuntimeException("Amende introuvable.");
        }

        amende.setStatutPaiement(Amende.StatutPaiement.PAYEE);
        amendeDao.update(amende);
    }
    public java.math.BigDecimal totalAmendesPayees() {
        return amendeDao.sommeAmendesPayees();
    }

    public java.math.BigDecimal totalAmendesNonPayees() {
        return amendeDao.sommeAmendesNonPayees();
    }

    public List<Amende> listerToutesLesAmendes() {
        return amendeDao.findAll();
    }
}