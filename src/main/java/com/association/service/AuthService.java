package com.association.service;

import com.association.dao.UtilisateurDao;
import com.association.model.Utilisateur;
import org.mindrot.jbcrypt.BCrypt;

public class AuthService {

    private final UtilisateurDao utilisateurDao = new UtilisateurDao();

    public Utilisateur login(String email, String motDePasse) {

        Utilisateur utilisateur = utilisateurDao.findByEmail(email);

        if (utilisateur == null) {
            return null;
        }

        if (!utilisateur.getActif()) {
            return null;
        }

        boolean motDePasseCorrect = BCrypt.checkpw(
                motDePasse,
                utilisateur.getMotDePasse()
        );

        if (!motDePasseCorrect) {
            return null;
        }

        return utilisateur;
    }

    public String hashPassword(String motDePasse) {
        return BCrypt.hashpw(motDePasse, BCrypt.gensalt());
    }
}