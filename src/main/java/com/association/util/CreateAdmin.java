package com.association.util;

import com.association.dao.UtilisateurDao;
import com.association.model.Utilisateur;
import com.association.service.AuthService;

public class CreateAdmin {

    public static void main(String[] args) {

        AuthService authService = new AuthService();
        UtilisateurDao utilisateurDao = new UtilisateurDao();

        Utilisateur admin = new Utilisateur();
        admin.setEmail("admin@gmail.com");
        admin.setMotDePasse(authService.hashPassword("admin123"));
        admin.setRole(Utilisateur.Role.ADMIN);
        admin.setActif(true);

        utilisateurDao.save(admin);

        System.out.println("Admin créé avec succès ✅");
    }
}