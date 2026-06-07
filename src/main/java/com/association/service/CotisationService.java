package com.association.service;

import com.association.dao.CampagneCotisationDao;
import com.association.dao.CotisationDao;
import com.association.dao.MembreDao;
import com.association.model.CampagneCotisation;
import com.association.model.Cotisation;
import com.association.model.Membre;
import com.association.util.ValidationUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CotisationService {

    private final CotisationDao cotisationDao = new CotisationDao();
    private final MembreDao membreDao = new MembreDao();
    private final CampagneCotisationDao campagneDao = new CampagneCotisationDao();
    private final NotificationService notificationService =
            new NotificationService();
    private final ParticipationCampagneService participationService =
            new ParticipationCampagneService();

    public Cotisation enregistrerCotisation(
            Long membreId,
            Long campagneId,
            LocalDate dateEcheance,
            String modePaiement
    ) {
        return enregistrerCotisation(
                membreId,
                campagneId,
                dateEcheance,
                modePaiement,
                Cotisation.StatutCotisation.PAYEE
        );
    }

    public Cotisation payerCotisationMembre(
            Long membreId,
            Long campagneId,
            LocalDate dateEcheance
    ) {
        Cotisation cotisation = enregistrerCotisation(
                membreId,
                campagneId,
                dateEcheance,
                "Paiement simulé",
                Cotisation.StatutCotisation.PAYEE
        );

        try {
            notificationService.notifierPaiementSimule(cotisation);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return cotisation;
    }

    private Cotisation enregistrerCotisation(
            Long membreId,
            Long campagneId,
            LocalDate dateEcheance,
            String modePaiement,
            Cotisation.StatutCotisation statut
    ) {
        ValidationUtil.idPositif(membreId, "Le membre");
        ValidationUtil.idPositif(campagneId, "La campagne");
        modePaiement = ValidationUtil.texteObligatoire(
                modePaiement,
                "Le mode de paiement",
                50
        );

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
        cotisation.setReferenceTransaction(genererReference());
        cotisation.setStatut(statut);

        cotisationDao.save(cotisation);
        return cotisation;
    }

    private String genererReference() {
        return "TXN-"
                + LocalDate.now().toString().replace("-", "")
                + "-"
                + UUID.randomUUID().toString()
                .substring(0, 8)
                .toUpperCase();
    }

    public void validerPaiement(Long cotisationId) {
        Cotisation cotisation = cotisationDao.findById(cotisationId);

        if (cotisation == null) {
            throw new RuntimeException("Paiement introuvable.");
        }

        cotisation.setStatut(Cotisation.StatutCotisation.PAYEE);
        cotisationDao.update(cotisation);
    }

    public Cotisation rechercherParId(Long cotisationId) {
        return cotisationDao.findById(cotisationId);
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
        CampagneCotisation campagne = campagneDao.findById(campagneId);
        List<Membre> resultat = new ArrayList<>();

        if (campagne == null) {
            return resultat;
        }

        for (Membre membre
                : participationService.listerMembresParticipants(campagne)) {
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

    public List<Membre> membresEnRetard(Long campagneId) {
        CampagneCotisation campagne = campagneDao.findById(campagneId);
        List<Membre> resultat = new ArrayList<>();

        if (campagne == null) {
            return resultat;
        }

        LocalDate fin = LocalDate.now().minusDays(1);

        if (campagne.getDateFin() != null && campagne.getDateFin().isBefore(fin)) {
            fin = campagne.getDateFin();
        }

        for (Membre membre
                : participationService.listerMembresParticipants(campagne)) {
            LocalDate echeance = campagne.getDateDebut();

            while (!echeance.isAfter(fin)) {
                if (!cotisationDao.membreAPayeEcheance(
                        membre.getId(),
                        campagneId,
                        echeance
                )) {
                    resultat.add(membre);
                    break;
                }

                switch (campagne.getFrequence()) {
                    case JOURNALIER:
                        echeance = echeance.plusDays(1);
                        break;
                    case HEBDOMADAIRE:
                        echeance = echeance.plusWeeks(1);
                        break;
                    case MENSUEL:
                        echeance = echeance.plusMonths(1);
                        break;
                }
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
