package com.library.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jdk.nashorn.internal.objects.annotations.Property;

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

    // private long numeroVente;
    private Long numeroVente;

    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "GMT")
    private Date dateVente;


    @OneToMany(mappedBy = "vente", fetch = FetchType.LAZY)
    @Valid
    private List<LigneVente> ligneVentes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private Utilisateur utilisateur;

    private double totalVente;

    private String status;

    public Vente() {
        super();
    }

    public Vente(Long id) {
        this.id = id;
    }

    public Vente(Long id, Long numeroVente, Date dateVente, @Valid List<LigneVente> ligneVentes,
                 double totalVente, String status, Utilisateur utilisateur) {
        this.id = id;
        this.numeroVente = numeroVente;
        this.dateVente = dateVente;
        this.ligneVentes = ligneVentes;
        this.totalVente = totalVente;
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

