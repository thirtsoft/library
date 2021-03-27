package com.library.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "avoir")
public class Avoir extends AbstractEntity {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   // private long reference;
    private Long reference;

    private String libelle;

    private double soldeAvoir;

    private int nbreJours;

    private double totalAvoir;

    private String status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "GMT")
    private Date dateAvoir;

    @ManyToOne
    @JoinColumn(name = "four_id")
    private Fournisseur fournisseur;

    @OneToMany(mappedBy = "avoir", fetch = FetchType.LAZY)
    @Valid
    private List<LigneAvoir> lavoirs = new ArrayList<>();

    public Avoir() {
    }

    public Avoir(Long id, Long reference, String libelle, double soldeAvoir, int nbreJours, double totalAvoir, String status, Date dateAvoir, Fournisseur fournisseur, @Valid List<LigneAvoir> lavoirs) {
        this.id = id;
        this.reference = reference;
        this.libelle = libelle;
        this.soldeAvoir = soldeAvoir;
        this.nbreJours = nbreJours;
        this.totalAvoir = totalAvoir;
        this.status = status;
        this.dateAvoir = dateAvoir;
        this.fournisseur = fournisseur;
        this.lavoirs = lavoirs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReference() {
        return reference;
    }

    public void setReference(Long reference) {
        this.reference = reference;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public double getSoldeAvoir() {
        return soldeAvoir;
    }

    public void setSoldeAvoir(double soldeAvoir) {
        this.soldeAvoir = soldeAvoir;
    }

    public int getNbreJours() {
        return nbreJours;
    }

    public void setNbreJours(int nbreJours) {
        this.nbreJours = nbreJours;
    }

    public double getTotalAvoir() {
        return totalAvoir;
    }

    public void setTotalAvoir(double totalAvoir) {
        this.totalAvoir = totalAvoir;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateAvoir() {
        return dateAvoir;
    }

    public void setDateAvoir(Date dateAvoir) {
        this.dateAvoir = dateAvoir;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public List<LigneAvoir> getLavoirs() {
        return lavoirs;
    }

    public void setLavoirs(List<LigneAvoir> lavoirs) {
        this.lavoirs = lavoirs;
    }
}
