package com.association.service;

import com.association.dao.MembreDao;
import com.association.dao.UtilisateurDao;
import com.association.model.Membre;
import com.association.model.Utilisateur;

import java.time.LocalDate;

public class MembreService {

    private final MembreDao membreDao = new MembreDao();
    private final UtilisateurDao utilisateurDao = new UtilisateurDao();
    private final AuthService authService = new AuthService();

    public java.util.List<Membre> listerTousLesMembres() {
        return membreDao.findAll();
    }
    public Membre rechercherParId(Long id) {
        return membreDao.findById(id);
    }

    public void supprimerMembre(Long id) {
        membreDao.delete(id);
    }
    public void modifierMembre(Long id, String numero, String prenom, String nom, String statut) {

        Membre membre = membreDao.findById(id);

        if (membre == null) {
            throw new RuntimeException("Membre introuvable.");
        }

        membre.setNumero(numero);
        membre.setPrenom(prenom);
        membre.setNom(nom);
        membre.setStatut(Membre.Statut.valueOf(statut));

        membreDao.update(membre);
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
            String motDePasse
    ) {

        // Vérifier si email existe déjà
        Utilisateur utilisateurExistant =
                utilisateurDao.findByEmail(email);

        if (utilisateurExistant != null) {
            throw new RuntimeException(
                    "Cet email existe déjà."
            );
        }

        // Création utilisateur
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

        membre.setDateAdhesion(LocalDate.now());

        membre.setUtilisateur(utilisateur);

        membreDao.save(membre);
    }
    public long compterMembres() {
        return membreDao.countAll();
    }
}