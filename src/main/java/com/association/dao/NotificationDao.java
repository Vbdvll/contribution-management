package com.association.dao;

import com.association.model.Notification;
import com.association.util.JpaUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class NotificationDao {

    public void save(Notification notification) {

        EntityManager em =
                JpaUtil.getEntityManager();

        try {

            em.getTransaction().begin();

            em.persist(notification);

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

    public boolean existeCleUnique(String cleUnique) {
        if (cleUnique == null || cleUnique.isBlank()) {
            return false;
        }

        EntityManager em = JpaUtil.getEntityManager();

        try {
            Long count = em.createQuery(
                            "SELECT COUNT(n) FROM Notification n "
                                    + "WHERE n.cleUnique = :cle",
                            Long.class
                    )
                    .setParameter("cle", cleUnique)
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }

    public List<Notification> findByUtilisateur(
            Long utilisateurId
    ) {

        EntityManager em =
                JpaUtil.getEntityManager();

        try {

            return em.createQuery(
                            "SELECT n FROM Notification n " +
                                    "WHERE n.utilisateur.id=:id " +
                                    "ORDER BY n.dateCreation DESC",
                            Notification.class
                    )
                    .setParameter("id", utilisateurId)
                    .getResultList();

        } finally {
            em.close();
        }
    }

    public List<Notification> findRecentByUtilisateur(
            Long utilisateurId,
            int limit
    ) {

        EntityManager em =
                JpaUtil.getEntityManager();

        try {

            return em.createQuery(
                            "SELECT n FROM Notification n " +
                                    "WHERE n.utilisateur.id=:id " +
                                    "ORDER BY n.dateCreation DESC",
                            Notification.class
                    )
                    .setParameter("id", utilisateurId)
                    .setMaxResults(limit)
                    .getResultList();

        } finally {
            em.close();
        }
    }

    public long countNonLues(
            Long utilisateurId
    ) {

        EntityManager em =
                JpaUtil.getEntityManager();

        try {

            return em.createQuery(
                            "SELECT COUNT(n) " +
                                    "FROM Notification n " +
                                    "WHERE n.utilisateur.id=:id " +
                                    "AND n.lu=false",
                            Long.class
                    )
                    .setParameter("id", utilisateurId)
                    .getSingleResult();

        } finally {
            em.close();
        }
    }

    public void marquerToutesCommeLues(
            Long utilisateurId
    ) {

        EntityManager em =
                JpaUtil.getEntityManager();

        try {

            em.getTransaction().begin();

            em.createQuery(
                            "UPDATE Notification n " +
                                    "SET n.lu=true " +
                                    "WHERE n.utilisateur.id=:id " +
                                    "AND n.lu=false"
                    )
                    .setParameter("id", utilisateurId)
                    .executeUpdate();

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
}
