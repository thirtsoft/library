package com.library.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "vente")
public class Vente extends AbstractEntity implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long numeroVente;

    private double totalVente;

    private String status;

    private String typeReglement;

    private double montantReglement;

    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "GMT")
    private Date dateVente;

    @OneToMany(mappedBy = "vente", fetch = FetchType.LAZY)
    @Valid
    private List<LigneVente> ligneVentes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private Utilisateur utilisateur;


    public Vente() {
        super();
    }

    public Vente(Long id) {
        this.id = id;
    }

    public Vente(Long id, Long numeroVente, Date dateVente, @Valid List<LigneVente> ligneVentes,
                 double totalVente, String typeReglement, double montantReglement, String status, Utilisateur utilisateur) {
        this.id = id;
        this.numeroVente = numeroVente;
        this.dateVente = dateVente;
        this.ligneVentes = ligneVentes;
        this.totalVente = totalVente;
        this.typeReglement = typeReglement;
        this.montantReglement = montantReglement;
        this.status = status;
        this.utilisateur = utilisateur;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroVente() {
        return numeroVente;
    }

    public void setNumeroVente(Long numeroVente) {
        this.numeroVente = numeroVente;
    }

    public Date getDateVente() {
        return dateVente;
    }

    public void setDateVente(Date dateVente) {
        this.dateVente = dateVente;
    }

    public List<LigneVente> getLigneVentes() {
        return ligneVentes;
    }

    public void setLigneVentes(List<LigneVente> ligneVentes) {
        this.ligneVentes = ligneVentes;
    }

    public double getTotalVente() {
        return totalVente;
    }

    public void setTotalVente(double totalVente) {
        this.totalVente = totalVente;
    }

    public String getTypeReglement() {
        return typeReglement;
    }

    public void setTypeReglement(String typeReglement) {
        this.typeReglement = typeReglement;
    }

    public double getMontantReglement() {
        return montantReglement;
    }

    public void setMontantReglement(double montantReglement) {
        this.montantReglement = montantReglement;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}

