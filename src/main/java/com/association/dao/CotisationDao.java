package com.association.dao;

import com.association.model.Cotisation;
import com.association.util.JpaUtil;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    public void update(Cotisation cotisation) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.merge(cotisation);
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

    public Cotisation findById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            return em.find(Cotisation.class, id);

        } finally {
            em.close();
        }
    }

    public List<Cotisation> findAll() {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT c FROM Cotisation c ORDER BY c.datePaiement DESC",
                    Cotisation.class
            ).getResultList();

        } finally {
            em.close();
        }
    }

    public List<Cotisation> findByCampagne(Long campagneId) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            return em.createQuery(
                            "SELECT c FROM Cotisation c " +
                                    "WHERE c.campagne.id = :campagneId " +
                                    "ORDER BY c.dateEcheance DESC",
                            Cotisation.class
                    )
                    .setParameter("campagneId", campagneId)
                    .getResultList();

        } finally {
            em.close();
        }
    }

    public List<Cotisation> findByMembre(Long membreId) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            return em.createQuery(
                            "SELECT c FROM Cotisation c " +
                                    "WHERE c.membre.id = :membreId " +
                                    "ORDER BY c.dateEcheance DESC",
                            Cotisation.class
                    )
                    .setParameter("membreId", membreId)
                    .getResultList();

        } finally {
            em.close();
        }
    }

    public Cotisation findByMembreCampagneEtEcheance(
            Long membreId,
            Long campagneId,
            LocalDate dateEcheance
    ) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            List<Cotisation> result = em.createQuery(
                            "SELECT c FROM Cotisation c " +
                                    "WHERE c.membre.id = :membreId " +
                                    "AND c.campagne.id = :campagneId " +
                                    "AND c.dateEcheance = :dateEcheance",
                            Cotisation.class
                    )
                    .setParameter("membreId", membreId)
                    .setParameter("campagneId", campagneId)
                    .setParameter("dateEcheance", dateEcheance)
                    .getResultList();

            return result.isEmpty() ? null : result.get(0);

        } finally {
            em.close();
        }
    }

    public boolean membreAPayeEcheance(
            Long membreId,
            Long campagneId,
            LocalDate dateEcheance
    ) {
        return findByMembreCampagneEtEcheance(
                membreId,
                campagneId,
                dateEcheance
        ) != null;
    }

    public BigDecimal sommeTotaleCotisations() {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT COALESCE(SUM(c.montant), 0) " +
                            "FROM Cotisation c " +
                            "WHERE c.statut = 'PAYEE'",
                    BigDecimal.class
            ).getSingleResult();

        } finally {
            em.close();
        }
    }

    public long countPaiementsEnAttente() {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT COUNT(c) FROM Cotisation c WHERE c.statut = 'EN_ATTENTE'",
                    Long.class
            ).getSingleResult();

        } finally {
            em.close();
        }
    }
}