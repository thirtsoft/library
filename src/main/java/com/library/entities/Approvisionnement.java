package com.library.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "approvisionnement")
public class Approvisionnement extends AbstractEntity {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // private long code;
    private Long code;

    private double montantAvance;

    private double totalAppro;

    // @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT")
    private Date dateApprovisionnement;

    private String status;

    private String observation;

    @ManyToOne
    @JoinColumn(name = "four_id")
    private Fournisseur fournisseur;

    @OneToMany(mappedBy = "approvisionnement", fetch = FetchType.LAZY)
    @Valid
    private List<LigneApprovisionnement> ligneApprovisionnements = new ArrayList<>();

    public Approvisionnement() {
    }

    public Approvisionnement(Long id, Long code, double montantAvance, double totalAppro, Date dateApprovisionnement, String status, String observation, Fournisseur fournisseur, @Valid List<LigneApprovisionnement> ligneApprovisionnements) {
        this.id = id;
        this.code = code;
        this.montantAvance = montantAvance;
        this.totalAppro = totalAppro;
        this.dateApprovisionnement = dateApprovisionnement;
        this.status = status;
        this.observation = observation;
        this.fournisseur = fournisseur;
        this.ligneApprovisionnements = ligneApprovisionnements;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public double getMontantAvance() {
        return montantAvance;
    }

    public void setMontantAvance(double montantAvance) {
        this.montantAvance = montantAvance;
    }

    public double getTotalAppro() {
        return totalAppro;
    }

    public void setTotalAppro(double totalAppro) {
        this.totalAppro = totalAppro;
    }

    public Date getDateApprovisionnement() {
        return dateApprovisionnement;
    }

    public void setDateApprovisionnement(Date dateApprovisionnement) {
        this.dateApprovisionnement = dateApprovisionnement;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public List<LigneApprovisionnement> getLigneApprovisionnements() {
        return ligneApprovisionnements;
    }

    public void setLigneApprovisionnements(List<LigneApprovisionnement> ligneApprovisionnements) {
        this.ligneApprovisionnements = ligneApprovisionnements;
    }
}
