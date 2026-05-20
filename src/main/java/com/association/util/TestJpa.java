package com.association.util;

import jakarta.persistence.EntityManager;

public class TestJpa {

    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntityManager();

        System.out.println("Connexion JPA/Hibernate réussie ✅");

        em.close();
        JpaUtil.close();
    }
}