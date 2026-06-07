package com.association.service;

import com.association.dao.AmendeDao;
import com.association.dao.CampagneCotisationDao;
import com.association.dao.MembreDao;
import com.association.model.Amende;
import com.association.model.CampagneCotisation;
import com.association.model.Membre;
import com.association.util.ValidationUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class AmendeService {

    private final AmendeDao amendeDao = new AmendeDao();
    private final MembreDao membreDao = new MembreDao();
    private final CampagneCotisationDao campagneDao = new CampagneCotisationDao();
    private final NotificationService notificationService = new NotificationService();
    private final CotisationService cotisationService = new CotisationService();
    private final ParticipationCampagneService participationService =
            new ParticipationCampagneService();

    public void genererAmende(Long membreId, Long campagneId, BigDecimal montant) {
        ValidationUtil.idPositif(membreId, "Le membre");
        ValidationUtil.idPositif(campagneId, "La campagne");
        if (montant == null || montant.signum() <= 0) {
            throw new RuntimeException("Le montant de l'amende doit être positif.");
        }

        boolean existe = amendeDao.existeAmende(membreId, campagneId);

        if (existe) {
            throw new RuntimeException("Une amende existe déjà pour cette campagne.");
        }

        Membre membre = membreDao.findById(membreId);

        if (membre == null) {
            throw new RuntimeException("Membre introuvable.");
        }

        if (membre.getStatut() != Membre.Statut.ACTIF) {
            throw new RuntimeException("Le membre est inactif.");
        }

        CampagneCotisation campagne = campagneDao.findById(campagneId);

        if (campagne == null) {
            throw new RuntimeException("Campagne introuvable.");
        }

        if (!participationService.membreParticipe(membreId, campagne)) {
            throw new RuntimeException(
                    "Ce membre ne participe pas a cette campagne."
            );
        }

        Amende amende = new Amende();

        amende.setMembre(membre);
        amende.setCampagne(campagne);
        amende.setMontant(montant);
        amende.setDateGeneration(LocalDate.now());
        amende.setStatutPaiement(Amende.StatutPaiement.NON_PAYEE);

        amendeDao.save(amende);

        notificationService.notifierAmendeGeneree(amende);
    }

    public List<Amende> listerToutesLesAmendes() {
        return amendeDao.findAll();
    }

    public int genererAmendesAutomatiques(Long campagneId) {
        ValidationUtil.idPositif(campagneId, "La campagne");
        CampagneCotisation campagne = campagneDao.findById(campagneId);

        if (campagne == null) {
            throw new RuntimeException("Campagne introuvable.");
        }

        BigDecimal montantAmende = campagne.getMontant()
                .multiply(new BigDecimal("0.10"))
                .setScale(2, RoundingMode.HALF_UP);

        int nombreAmendes = 0;

        for (Membre membre : cotisationService.membresEnRetard(campagneId)) {
            if (!amendeDao.existeAmende(membre.getId(), campagneId)) {
                genererAmende(membre.getId(), campagneId, montantAmende);
                nombreAmendes++;
            }
        }

        return nombreAmendes;
    }

    public List<Amende> listerAmendesParMembre(Long membreId) {
        return amendeDao.findByMembre(membreId);
    }

    public void marquerCommePayee(Long id) {
        ValidationUtil.idPositif(id, "L'amende");
        Amende amende = amendeDao.findById(id);

        if (amende == null) {
            throw new RuntimeException("Amende introuvable.");
        }

        amende.setStatutPaiement(Amende.StatutPaiement.PAYEE);

        amendeDao.update(amende);

        notificationService.notifierAmendePayee(amende);
    }

    public void payerParMembre(Long id, Long membreId) {
        ValidationUtil.idPositif(id, "L'amende");
        ValidationUtil.idPositif(membreId, "Le membre");
        Amende amende = amendeDao.findById(id);

        if (amende == null || !amende.getMembre().getId().equals(membreId)) {
            throw new RuntimeException("Amende introuvable.");
        }

        if (amende.getStatutPaiement() == Amende.StatutPaiement.PAYEE) {
            throw new RuntimeException("Cette amende est déjà payée.");
        }

        marquerCommePayee(id);
    }

    public BigDecimal totalAmendesPayees() {
        return amendeDao.sommeAmendesPayees();
    }

    public BigDecimal totalAmendesNonPayees() {
        return amendeDao.sommeAmendesNonPayees();
    }
}
