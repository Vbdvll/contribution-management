package com.association.dao;

import com.association.model.Membre;
import com.association.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;

public class MembreDao {

    public void save(Membre membre) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(membre);
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

    public List<Membre> findAll() {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT m FROM Membre m",
                    Membre.class
            ).getResultList();

        } finally {
            em.close();
        }
    }

    public Membre findById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            return em.find(Membre.class, id);

        } finally {
            em.close();
        }
    }

    public Membre findByUtilisateurId(Long utilisateurId) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            return em.createQuery(
                            "SELECT m FROM Membre m WHERE m.utilisateur.id = :utilisateurId",
                            Membre.class
                    )
                    .setParameter("utilisateurId", utilisateurId)
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;

        } finally {
            em.close();
        }
    }

    public void update(Membre membre) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.merge(membre);
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

    public void delete(Long id) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            Membre membre = em.find(Membre.class, id);

            if (membre != null) {
                em.remove(membre);
            }

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

    public long countAll() {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT COUNT(m) FROM Membre m",
                    Long.class
            ).getSingleResult();

        } finally {
            em.close();
        }
    }

    public List<Membre> rechercher(String motCle) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            return em.createQuery(
                            "SELECT m FROM Membre m " +
                                    "WHERE LOWER(m.nom) LIKE :motCle " +
                                    "OR LOWER(m.prenom) LIKE :motCle " +
                                    "OR LOWER(m.numero) LIKE :motCle " +
                                    "OR LOWER(m.utilisateur.email) LIKE :motCle",
                            Membre.class
                    )
                    .setParameter("motCle", "%" + motCle.toLowerCase() + "%")
                    .getResultList();

        } finally {
            em.close();
        }
    }
}