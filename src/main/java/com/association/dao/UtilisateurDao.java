package com.association.dao;
import java.util.List;
import com.association.model.Utilisateur;
import com.association.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UtilisateurDao {

    public void save(Utilisateur utilisateur) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(utilisateur);
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

    public Utilisateur findByEmail(String email) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            TypedQuery<Utilisateur> query = em.createQuery(
                    "SELECT u FROM Utilisateur u WHERE u.email = :email",
                    Utilisateur.class
            );

            query.setParameter("email", email);

            return query.getSingleResult();

        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    public Utilisateur findById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            return em.find(Utilisateur.class, id);

        } finally {
            em.close();
        }
    }

    public void update(Utilisateur utilisateur) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.merge(utilisateur);
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

    public List<Utilisateur> findAdmins() {

        EntityManager em =
                JpaUtil.getEntityManager();

        try {

            return em.createQuery(
                    "SELECT u FROM Utilisateur u WHERE u.role='ADMIN'",
                    Utilisateur.class
            ).getResultList();

        } finally {
            em.close();
        }
    }
}
