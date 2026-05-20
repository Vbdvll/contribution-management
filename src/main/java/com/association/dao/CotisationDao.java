package com.association.dao;

import com.association.model.Cotisation;
import com.association.util.JpaUtil;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.List;

public class CotisationDao {

    public void save(Cotisation cotisation) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(cotisation);
            em.getTransaction().commit();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();

        } finally {
            em.close();
        }
    }

    public List<Cotisation> findAll() {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT c FROM Cotisation c",
                    Cotisation.class
            ).getResultList();

        } finally {
            em.close();
        }
    }

    public BigDecimal sommeTotaleCotisations() {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT COALESCE(SUM(c.montant), 0) FROM Cotisation c",
                    BigDecimal.class
            ).getSingleResult();

        } finally {
            em.close();
        }
    }

    public List<Cotisation> filtrer(Integer mois, Integer annee, String statut) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            String jpql = "SELECT c FROM Cotisation c WHERE 1=1 ";

            if (mois != null) {
                jpql += "AND c.mois = :mois ";
            }

            if (annee != null) {
                jpql += "AND c.annee = :annee ";
            }

            if (statut != null && !statut.isEmpty()) {
                jpql += "AND c.statut = :statut ";
            }

            var query = em.createQuery(jpql, Cotisation.class);

            if (mois != null) {
                query.setParameter("mois", mois);
            }

            if (annee != null) {
                query.setParameter("annee", annee);
            }

            if (statut != null && !statut.isEmpty()) {
                query.setParameter(
                        "statut",
                        Cotisation.StatutCotisation.valueOf(statut)
                );
            }

            return query.getResultList();

        } finally {
            em.close();
        }
    }

    public boolean existeCotisationPourMembreEtPeriode(
            Long membreId,
            Integer mois,
            Integer annee
    ) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            Long count = em.createQuery(
                            "SELECT COUNT(c) FROM Cotisation c " +
                                    "WHERE c.membre.id = :membreId " +
                                    "AND c.mois = :mois " +
                                    "AND c.annee = :annee",
                            Long.class
                    )
                    .setParameter("membreId", membreId)
                    .setParameter("mois", mois)
                    .setParameter("annee", annee)
                    .getSingleResult();

            return count > 0;

        } finally {
            em.close();
        }
    }
    public boolean membreAPayeCampagne(Long membreId, Long campagneId) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            Long count = em.createQuery(
                            "SELECT COUNT(c) FROM Cotisation c " +
                                    "WHERE c.membre.id = :membreId " +
                                    "AND c.campagne.id = :campagneId",
                            Long.class
                    )
                    .setParameter("membreId", membreId)
                    .setParameter("campagneId", campagneId)
                    .getSingleResult();

            return count > 0;

        } finally {
            em.close();
        }
    }
}