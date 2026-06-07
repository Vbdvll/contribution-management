package com.association.service;

import com.association.dao.CampagneCotisationDao;
import com.association.dao.MembreDao;
import com.association.model.CampagneCotisation;
import com.association.model.Membre;
import com.association.util.ValidationUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class CampagneCotisationService {

    private final CampagneCotisationDao campagneDao =
            new CampagneCotisationDao();
    private final MembreDao membreDao = new MembreDao();
    private final NotificationService notificationService =
            new NotificationService();

    public void creerCampagne(
            String titre,
            BigDecimal montant,
            String frequence,
            LocalDate dateDebut,
            LocalDate dateFin,
            boolean retardTolere,
            String typeParticipation
    ) {
        titre = ValidationUtil.texteObligatoire(titre, "Le titre", 150);

        if (montant == null || montant.signum() <= 0) {
            throw new RuntimeException("Le montant doit être positif.");
        }

        if (dateDebut == null) {
            throw new RuntimeException("La date de début est obligatoire.");
        }

        if (dateFin != null && dateFin.isBefore(dateDebut)) {
            throw new RuntimeException("La date de fin doit suivre la date de début.");
        }

        CampagneCotisation campagne = new CampagneCotisation();

        campagne.setTitre(titre);
        campagne.setMontant(montant);
        try {
            campagne.setFrequence(
                    CampagneCotisation.Frequence.valueOf(frequence)
            );
        } catch (Exception e) {
            throw new RuntimeException("La frequence est invalide.");
        }
        campagne.setDateDebut(dateDebut);
        campagne.setDateFin(dateFin);
        campagne.setRetardTolere(retardTolere);
        campagne.setStatut(CampagneCotisation.Statut.ACTIVE);
        try {
            campagne.setTypeParticipation(
                    CampagneCotisation.TypeParticipation.valueOf(
                            typeParticipation
                    )
            );
        } catch (Exception e) {
            throw new RuntimeException("Le type de participation est invalide.");
        }

        if (campagne.getTypeParticipation()
                == CampagneCotisation.TypeParticipation.SUR_INSCRIPTION) {
            campagne.setCodeInscription(genererCodeInscription());
        }

        campagneDao.save(campagne);

        if (campagne.getTypeParticipation()
                == CampagneCotisation.TypeParticipation.OBLIGATOIRE) {
            for (Membre membre : membreDao.findActifs()) {
                try {
                    notificationService.notifierCampagneLancee(
                            membre.getUtilisateur().getId(),
                            campagne.getId(),
                            campagne.getTitre(),
                            campagne.getMontant().toString(),
                            campagne.getDateDebut()
                    );
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        }
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

    private String genererCodeInscription() {
        String code;

        do {
            code = "COT-" + UUID.randomUUID()
                    .toString()
                    .substring(0, 8)
                    .toUpperCase();
        } while (campagneDao.findByCode(code) != null);

        return code;
    }
}
