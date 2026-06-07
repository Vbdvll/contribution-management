package com.association.service;

import com.association.dao.NotificationDao;
import com.association.dao.UtilisateurDao;
import com.association.model.Amende;
import com.association.model.Cotisation;
import com.association.model.Notification;
import com.association.model.Utilisateur;

import java.time.LocalDate;
import java.util.List;

public class NotificationService {

    private final NotificationDao notificationDao =
            new NotificationDao();

    private final UtilisateurDao utilisateurDao =
            new UtilisateurDao();

    public void creerNotification(
            Long utilisateurId,
            String titre,
            String message,
            String type
    ) {
        creerNotificationUnique(
                utilisateurId,
                titre,
                message,
                type,
                null
        );
    }

    public void creerNotificationUnique(
            Long utilisateurId,
            String titre,
            String message,
            String type,
            String cleUnique
    ) {
        if (notificationDao.existeCleUnique(cleUnique)) {
            return;
        }

        Utilisateur utilisateur =
                utilisateurDao.findById(utilisateurId);

        if (utilisateur == null) {
            return;
        }

        Notification notification =
                new Notification();

        notification.setUtilisateur(utilisateur);
        notification.setTitre(titre);
        notification.setMessage(message);
        notification.setType(type);
        notification.setCleUnique(cleUnique);
        notification.setLu(false);

        notificationDao.save(notification);
    }

    public void notifierTousLesAdmins(
            String titre,
            String message,
            String type
    ) {
        List<Utilisateur> admins =
                utilisateurDao.findAdmins();

        for (Utilisateur admin : admins) {
            creerNotification(
                    admin.getId(),
                    titre,
                    message,
                    type
            );
        }
    }

    public void notifierPaiementDeclare(
            Cotisation cotisation
    ) {
        if (cotisation == null
                || cotisation.getMembre() == null
                || cotisation.getCampagne() == null) {
            return;
        }

        String titre =
                "Paiement en attente";

        String message =
                cotisation.getMembre().getPrenom()
                        + " "
                        + cotisation.getMembre().getNom()
                        + " a déclaré un paiement pour la campagne "
                        + cotisation.getCampagne().getTitre()
                        + ", échéance "
                        + cotisation.getDateEcheance()
                        + ".";

        notifierTousLesAdmins(
                titre,
                message,
                "PAIEMENT"
        );
    }

    public void notifierPaiementValide(
            Cotisation cotisation
    ) {
        if (cotisation == null
                || cotisation.getMembre() == null
                || cotisation.getMembre().getUtilisateur() == null
                || cotisation.getCampagne() == null) {
            return;
        }

        String titre =
                "Paiement validé";

        String message =
                "Votre paiement pour la campagne "
                        + cotisation.getCampagne().getTitre()
                        + ", échéance "
                        + cotisation.getDateEcheance()
                        + ", a été validé.";

        creerNotification(
                cotisation.getMembre().getUtilisateur().getId(),
                titre,
                message,
                "VALIDATION_PAIEMENT"
        );
    }

    public void notifierPaiementSimule(Cotisation cotisation) {
        if (cotisation == null
                || cotisation.getMembre() == null
                || cotisation.getMembre().getUtilisateur() == null
                || cotisation.getCampagne() == null) {
            return;
        }

        Long utilisateurId =
                cotisation.getMembre().getUtilisateur().getId();
        String reference = cotisation.getReferenceTransaction();

        creerNotificationUnique(
                utilisateurId,
                "Paiement confirmé",
                "Votre paiement de "
                        + cotisation.getMontant()
                        + " FCFA pour la campagne "
                        + cotisation.getCampagne().getTitre()
                        + " a été confirmé. Référence : "
                        + reference
                        + ".",
                "PAIEMENT_CONFIRME",
                "PAIEMENT-MEMBRE-" + reference
        );

        for (Utilisateur admin : utilisateurDao.findAdmins()) {
            creerNotificationUnique(
                    admin.getId(),
                    "Nouveau paiement confirmé",
                    cotisation.getMembre().getPrenom()
                            + " "
                            + cotisation.getMembre().getNom()
                            + " a payé "
                            + cotisation.getMontant()
                            + " FCFA. Référence : "
                            + reference
                            + ".",
                    "PAIEMENT_CONFIRME",
                    "PAIEMENT-ADMIN-" + admin.getId() + "-" + reference
            );
        }
    }

    public void notifierCampagneLancee(
            Long utilisateurId,
            Long campagneId,
            String titreCampagne,
            String montant,
            LocalDate premiereEcheance
    ) {
        creerNotificationUnique(
                utilisateurId,
                "Nouvelle campagne de cotisation",
                "La campagne "
                        + titreCampagne
                        + " est lancée. Montant : "
                        + montant
                        + " FCFA. Première échéance : "
                        + premiereEcheance
                        + ".",
                "CAMPAGNE",
                "CAMPAGNE-" + campagneId + "-U-" + utilisateurId
        );
    }

    public void notifierEcheance(
            Long utilisateurId,
            Long campagneId,
            String titreCampagne,
            String montant,
            LocalDate dateEcheance
    ) {
        creerNotificationUnique(
                utilisateurId,
                "Échéance de cotisation",
                "Votre cotisation de "
                        + montant
                        + " FCFA pour la campagne "
                        + titreCampagne
                        + " est à payer aujourd'hui.",
                "RAPPEL_ECHEANCE",
                "RAPPEL-" + campagneId + "-" + dateEcheance + "-U-" + utilisateurId
        );
    }

    public void notifierAmendeGeneree(
            Amende amende
    ) {
        if (amende == null
                || amende.getMembre() == null
                || amende.getMembre().getUtilisateur() == null
                || amende.getCampagne() == null) {
            return;
        }

        String titre =
                "Nouvelle amende";

        String message =
                "Une amende de "
                        + amende.getMontant()
                        + " FCFA a été générée pour la campagne "
                        + amende.getCampagne().getTitre()
                        + ".";

        creerNotification(
                amende.getMembre().getUtilisateur().getId(),
                titre,
                message,
                "AMENDE"
        );
    }

    public void notifierAmendePayee(
            Amende amende
    ) {
        if (amende == null
                || amende.getMembre() == null
                || amende.getMembre().getUtilisateur() == null
                || amende.getCampagne() == null) {
            return;
        }

        String titre =
                "Amende réglée";

        String message =
                "Votre amende liée à la campagne "
                        + amende.getCampagne().getTitre()
                        + " a été marquée comme payée.";

        creerNotification(
                amende.getMembre().getUtilisateur().getId(),
                titre,
                message,
                "AMENDE_PAYEE"
        );
    }

    public List<Notification> listerNotificationsUtilisateur(
            Long utilisateurId
    ) {
        return notificationDao.findByUtilisateur(
                utilisateurId
        );
    }

    public List<Notification> listerNotificationsRecentes(
            Long utilisateurId
    ) {
        return notificationDao.findRecentByUtilisateur(
                utilisateurId,
                3
        );
    }

    public long compterNotificationsNonLues(
            Long utilisateurId
    ) {
        return notificationDao.countNonLues(
                utilisateurId
        );
    }

    public void marquerToutesCommeLues(
            Long utilisateurId
    ) {
        notificationDao.marquerToutesCommeLues(
                utilisateurId
        );
    }
}
