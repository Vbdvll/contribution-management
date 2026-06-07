package com.association.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    @Column(nullable = false, length = 150)
    private String titre;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(nullable = false, length = 50)
    private String type;

    @Column(name = "cle_unique", unique = true, length = 150)
    private String cleUnique;

    @Column(nullable = false)
    private boolean lu = false;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation = LocalDateTime.now();

    public Notification() {
    }

    public Long getId() {
        return id;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public String getTitre() {
        return titre;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public String getCleUnique() {
        return cleUnique;
    }

    public boolean isLu() {
        return lu;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCleUnique(String cleUnique) {
        this.cleUnique = cleUnique;
    }

    public void setLu(boolean lu) {
        this.lu = lu;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }
}
