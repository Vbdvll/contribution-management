package com.association.service;

import com.association.dao.UtilisateurDao;
import com.association.model.Utilisateur;
import com.association.util.ValidationUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.mindrot.jbcrypt.BCrypt;

@ApplicationScoped
public class AuthService {

    private final UtilisateurDao utilisateurDao;

    public AuthService() {
        this(new UtilisateurDao());
    }

    @Inject
    public AuthService(UtilisateurDao utilisateurDao) {
        this.utilisateurDao = utilisateurDao;
    }

    public Utilisateur login(String email, String motDePasse) {
        if (email == null || email.isBlank()
                || motDePasse == null || motDePasse.isBlank()) {
            return null;
        }

        Utilisateur utilisateur =
                utilisateurDao.findByEmail(email.trim().toLowerCase());

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
        return BCrypt.hashpw(
                ValidationUtil.motDePasse(motDePasse),
                BCrypt.gensalt()
        );
    }
}
