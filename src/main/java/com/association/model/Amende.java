package com.association.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "amende")
public class Amende {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "membre_id", nullable = false)
    private Membre membre;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal montant;

    @Column(name = "date_generation", nullable = false)
    private LocalDate dateGeneration;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut_paiement", nullable = false)
    private StatutPaiement statutPaiement = StatutPaiement.NON_PAYEE;

    public enum StatutPaiement {
        PAYEE,
        NON_PAYEE
    }

    public Amende() {
    }

    public Amende(Membre membre, BigDecimal montant, LocalDate dateGeneration) {
        this.membre = membre;
        this.montant = montant;
        this.dateGeneration = dateGeneration;
    }

    public Long getId() {
        return id;
    }

    public Membre getMembre() {
        return membre;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public LocalDate getDateGeneration() {
        return dateGeneration;
    }

    public StatutPaiement getStatutPaiement() {
        return statutPaiement;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public void setDateGeneration(LocalDate dateGeneration) {
        this.dateGeneration = dateGeneration;
    }

    public void setStatutPaiement(StatutPaiement statutPaiement) {
        this.statutPaiement = statutPaiement;
    }
}