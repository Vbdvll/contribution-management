package com.association.dao;

import com.association.model.CampagneCotisation;
import com.association.util.JpaUtil;
import jakarta.persistence.EntityManager;

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
            e.printStackTrace();

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
}