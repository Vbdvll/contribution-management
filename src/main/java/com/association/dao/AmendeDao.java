package com.association.dao;

import com.association.model.Amende;
import com.association.util.JpaUtil;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.List;

public class AmendeDao {

    public void save(Amende amende) {

        EntityManager em = JpaUtil.getEntityManager();

        try {

            em.getTransaction().begin();

            em.persist(amende);

            em.getTransaction().commit();

        }
        catch(Exception e){

            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }

            e.printStackTrace();
        }
        finally{
            em.close();
        }
    }

    public List<Amende> findAll(){

        EntityManager em=JpaUtil.getEntityManager();

        try{

            return em.createQuery(
                    "SELECT a FROM Amende a ORDER BY a.dateGeneration DESC",
                    Amende.class
            ).getResultList();

        }finally{
            em.close();
        }
    }

    public Amende findById(Long id){

        EntityManager em=JpaUtil.getEntityManager();

        try{
            return em.find(
                    Amende.class,
                    id
            );
        }
        finally{
            em.close();
        }
    }

    public boolean existeAmende(
            Long membreId,
            Long campagneId
    ){

        EntityManager em=
                JpaUtil.getEntityManager();

        try{

            Long count=
                    em.createQuery(
                                    "SELECT COUNT(a) FROM Amende a " +
                                            "WHERE a.membre.id=:membreId " +
                                            "AND a.campagne.id=:campagneId",
                                    Long.class
                            )
                            .setParameter(
                                    "membreId",
                                    membreId
                            )
                            .setParameter(
                                    "campagneId",
                                    campagneId
                            )
                            .getSingleResult();

            return count>0;

        }
        finally{
            em.close();
        }
    }

    public void update(Amende amende){

        EntityManager em=
                JpaUtil.getEntityManager();

        try{

            em.getTransaction().begin();

            em.merge(amende);

            em.getTransaction().commit();

        }
        catch(Exception e){

            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }

            e.printStackTrace();
        }
        finally{
            em.close();
        }
    }

    public BigDecimal sommeAmendesPayees(){

        EntityManager em=
                JpaUtil.getEntityManager();

        try{

            return em.createQuery(
                    "SELECT COALESCE(SUM(a.montant),0) " +
                            "FROM Amende a " +
                            "WHERE a.statutPaiement='PAYEE'",
                    BigDecimal.class
            ).getSingleResult();

        }finally{
            em.close();
        }
    }

    public BigDecimal sommeAmendesNonPayees(){

        EntityManager em=
                JpaUtil.getEntityManager();

        try{

            return em.createQuery(
                    "SELECT COALESCE(SUM(a.montant),0) " +
                            "FROM Amende a " +
                            "WHERE a.statutPaiement='NON_PAYEE'",
                    BigDecimal.class
            ).getSingleResult();

        }finally{
            em.close();
        }
    }
}