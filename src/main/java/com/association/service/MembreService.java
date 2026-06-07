package com.association.service;

import com.association.dao.MembreDao;
import com.association.dao.UtilisateurDao;
import com.association.model.Membre;
import com.association.model.Utilisateur;
import com.association.util.ValidationUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.util.UUID;

@ApplicationScoped
public class MembreService {

    private final MembreDao membreDao;
    private final UtilisateurDao utilisateurDao;
    private final AuthService authService;

    public MembreService() {
        this(new MembreDao(), new UtilisateurDao(), new AuthService());
    }

    @Inject
    public MembreService(
            MembreDao membreDao,
            UtilisateurDao utilisateurDao,
            AuthService authService
    ) {
        this.membreDao = membreDao;
        this.utilisateurDao = utilisateurDao;
        this.authService = authService;
    }

    public java.util.List<Membre> listerTousLesMembres() {
        return membreDao.findAll();
    }
    public Membre rechercherParId(Long id) {
        return membreDao.findById(id);
    }

    public void desactiverMembre(Long id) {
        ValidationUtil.idPositif(id, "Le membre");
        Membre membre = membreDao.findById(id);

        if (membre == null) {
            throw new RuntimeException("Membre introuvable.");
        }

        membre.setStatut(Membre.Statut.INACTIF);
        membreDao.update(membre);

        Utilisateur utilisateur = membre.getUtilisateur();
        utilisateur.setActif(false);
        utilisateurDao.update(utilisateur);
    }
    public void modifierMembre(
            Long id,
            String numero,
            String prenom,
            String nom,
            String email,
            LocalDate dateNaissance,
            LocalDate dateAdhesion,
            String statut
    ) {
        ValidationUtil.idPositif(id, "Le membre");
        numero = ValidationUtil.texteObligatoire(numero, "Le numero", 50);
        prenom = ValidationUtil.texteObligatoire(prenom, "Le prenom", 100);
        nom = ValidationUtil.texteObligatoire(nom, "Le nom", 100);
        email = ValidationUtil.email(email);
        validerDates(dateNaissance, dateAdhesion);

        Membre membre = membreDao.findById(id);

        if (membre == null) {
            throw new RuntimeException("Membre introuvable.");
        }

        Membre memeNumero = membreDao.findByNumero(numero);
        if (memeNumero != null && !memeNumero.getId().equals(id)) {
            throw new RuntimeException("Ce numero de membre existe deja.");
        }

        Utilisateur utilisateurAvecEmail = utilisateurDao.findByEmail(email);
        if (utilisateurAvecEmail != null
                && !utilisateurAvecEmail.getId().equals(
                membre.getUtilisateur().getId()
        )) {
            throw new RuntimeException("Cet email existe deja.");
        }

        membre.setNumero(numero);
        membre.setPrenom(prenom);
        membre.setNom(nom);
        membre.setDateNaissance(dateNaissance);
        membre.setDateAdhesion(dateAdhesion);
        try {
            membre.setStatut(Membre.Statut.valueOf(statut));
        } catch (Exception e) {
            throw new RuntimeException("Le statut du membre est invalide.");
        }

        membreDao.update(membre);

        Utilisateur utilisateur = membre.getUtilisateur();
        utilisateur.setEmail(email);
        utilisateur.setActif(membre.getStatut() == Membre.Statut.ACTIF);
        utilisateurDao.update(utilisateur);
    }
    public java.util.List<Membre> rechercherMembres(String motCle) {

        if (motCle == null || motCle.trim().isEmpty()) {
            return membreDao.findAll();
        }

        return membreDao.rechercher(motCle);
    }
    public Membre rechercherParUtilisateurId(Long utilisateurId) {
        return membreDao.findByUtilisateurId(utilisateurId);
    }

    public void creerMembre(
            String numero,
            String prenom,
            String nom,
            String email,
            String motDePasse,
            LocalDate dateNaissance,
            LocalDate dateAdhesion
    ) {
        numero = ValidationUtil.texteObligatoire(numero, "Le numero", 50);
        prenom = ValidationUtil.texteObligatoire(prenom, "Le prenom", 100);
        nom = ValidationUtil.texteObligatoire(nom, "Le nom", 100);
        email = ValidationUtil.email(email);
        motDePasse = ValidationUtil.motDePasse(motDePasse);
        validerDates(dateNaissance, dateAdhesion);

        Utilisateur utilisateurExistant =
                utilisateurDao.findByEmail(email);

        if (utilisateurExistant != null) {
            throw new RuntimeException(
                    "Cet email existe déjà."
            );
        }

        if (membreDao.findByNumero(numero) != null) {
            throw new RuntimeException("Ce numero de membre existe deja.");
        }

        Utilisateur utilisateur = new Utilisateur();

        utilisateur.setEmail(email);

        utilisateur.setMotDePasse(
                authService.hashPassword(motDePasse)
        );

        utilisateur.setRole(Utilisateur.Role.MEMBRE);

        utilisateur.setActif(true);

        utilisateurDao.save(utilisateur);

        // Création membre
        Membre membre = new Membre();

        membre.setNumero(numero);

        membre.setPrenom(prenom);

        membre.setNom(nom);

        membre.setDateNaissance(dateNaissance);
        membre.setDateAdhesion(dateAdhesion);

        membre.setUtilisateur(utilisateur);

        membreDao.save(membre);
    }

    public void creerCompteMembre(
            String prenom,
            String nom,
            String email,
            String motDePasse,
            LocalDate dateNaissance
    ) {
        creerMembre(
                genererNumeroMembre(),
                prenom,
                nom,
                email,
                motDePasse,
                dateNaissance,
                LocalDate.now()
        );
    }

    public long compterMembres() {
        return membreDao.countAll();
    }

    private void validerDates(
            LocalDate dateNaissance,
            LocalDate dateAdhesion
    ) {
        if (dateNaissance == null) {
            throw new RuntimeException("La date de naissance est obligatoire.");
        }

        if (dateNaissance.isAfter(LocalDate.now())) {
            throw new RuntimeException("La date de naissance est invalide.");
        }

        if (dateAdhesion == null) {
            throw new RuntimeException("La date d'adhesion est obligatoire.");
        }

        if (dateAdhesion.isBefore(dateNaissance)) {
            throw new RuntimeException(
                    "La date d'adhesion doit suivre la date de naissance."
            );
        }
    }

    private String genererNumeroMembre() {
        String numero;

        do {
            numero = "M-" + UUID.randomUUID()
                    .toString()
                    .substring(0, 8)
                    .toUpperCase();
        } while (membreDao.findByNumero(numero) != null);

        return numero;
    }
}
