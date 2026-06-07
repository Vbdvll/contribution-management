package com.association.dao;

import com.association.model.CampagneCotisation;
import com.association.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.math.BigDecimal;
import java.util.List;

public class CampagneCotisationDao {

    public void save(CampagneCotisation campagne) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(campagne);
            em.getTransaction().commit();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;

        } finally {
            em.close();
        }
    }

    public void update(CampagneCotisation campagne) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.merge(campagne);
            em.getTransaction().commit();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;

        } finally {
            em.close();
        }
    }

    public List<CampagneCotisation> findAll() {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT c FROM CampagneCotisation c ORDER BY c.dateCreation DESC",
                    CampagneCotisation.class
            ).getResultList();

        } finally {
            em.close();
        }
    }

    public List<CampagneCotisation> findActives() {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT c FROM CampagneCotisation c WHERE c.statut = 'ACTIVE' ORDER BY c.dateCreation DESC",
                    CampagneCotisation.class
            ).getResultList();

        } finally {
            em.close();
        }
    }

    public CampagneCotisation findById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            return em.find(CampagneCotisation.class, id);

        } finally {
            em.close();
        }
    }

    public CampagneCotisation findByCode(String code) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            return em.createQuery(
                            "SELECT c FROM CampagneCotisation c "
                                    + "WHERE UPPER(c.codeInscription) = :code",
                            CampagneCotisation.class
                    )
                    .setParameter("code", code.toUpperCase())
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public long countActives() {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT COUNT(c) FROM CampagneCotisation c WHERE c.statut = 'ACTIVE'",
                    Long.class
            ).getSingleResult();

        } finally {
            em.close();
        }
    }

    public BigDecimal montantTotalAttendu() {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT COALESCE(SUM(c.montant), 0) FROM CampagneCotisation c",
                    BigDecimal.class
            ).getSingleResult();

        } finally {
            em.close();
        }
    }
}
