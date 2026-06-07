package com.association.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "cotisation")
public class Cotisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "membre_id", nullable = false)
    private Membre membre;

    @ManyToOne
    @JoinColumn(name = "campagne_id", nullable = false)
    private CampagneCotisation campagne;

    @Column(name = "date_echeance", nullable = false)
    private LocalDate dateEcheance;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal montant;

    @Column(nullable = false)
    private Integer mois;

    @Column(nullable = false)
    private Integer annee;

    @Column(name = "date_paiement", nullable = false)
    private LocalDate datePaiement;

    @Column(name = "mode_paiement", length = 50)
    private String modePaiement;

    @Column(name = "reference_transaction", nullable = false, unique = true, length = 50)
    private String referenceTransaction;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutCotisation statut = StatutCotisation.EN_ATTENTE;

    public enum StatutCotisation {
        PAYEE,
        EN_ATTENTE,
        EN_RETARD
    }

    public Cotisation() {
    }

    public Long getId() {
        return id;
    }

    public Membre getMembre() {
        return membre;
    }

    public CampagneCotisation getCampagne() {
        return campagne;
    }

    public LocalDate getDateEcheance() {
        return dateEcheance;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public Integer getMois() {
        return mois;
    }

    public Integer getAnnee() {
        return annee;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public String getModePaiement() {
        return modePaiement;
    }

    public String getReferenceTransaction() {
        return referenceTransaction;
    }

    public StatutCotisation getStatut() {
        return statut;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }

    public void setCampagne(CampagneCotisation campagne) {
        this.campagne = campagne;
    }

    public void setDateEcheance(LocalDate dateEcheance) {
        this.dateEcheance = dateEcheance;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public void setMois(Integer mois) {
        this.mois = mois;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }

    public void setModePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
    }

    public void setReferenceTransaction(String referenceTransaction) {
        this.referenceTransaction = referenceTransaction;
    }

    public void setStatut(StatutCotisation statut) {
        this.statut = statut;
    }
}
