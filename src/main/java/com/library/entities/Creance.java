package com.library.entities;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "creance",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "codeCreance")
        })
public class Creance extends AbstractEntity {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long reference;

    private String codeCreance;

    private String libelle;

    private double soldeCreance;

    private double avanceCreance;

    private int nbreJours;

    private double totalCreance;

    private String status;

    //  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "GMT")
    private Date dateCreance;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private Utilisateur utilisateur;

    @OneToMany(mappedBy = "creance", fetch = FetchType.LAZY)
    @Valid
    private List<LigneCreance> lcreances = new ArrayList<>();

    public Creance() {
        super();
    }

    public Creance(Long id, Long reference, String codeCreance, String libelle, double soldeCreance, double avanceCreance, int nbreJours, double totalCreance, String status, Date dateCreance, Client client, @Valid List<LigneCreance> lcreances) {
        this.id = id;
        this.reference = reference;
        this.libelle = libelle;
        this.codeCreance = codeCreance;
        this.soldeCreance = soldeCreance;
        this.avanceCreance = avanceCreance;
        this.nbreJours = nbreJours;
        this.totalCreance = totalCreance;
        this.status = status;
        this.dateCreance = dateCreance;
        this.client = client;
        this.lcreances = lcreances;
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

    public String getCodeCreance() {
        return codeCreance;
    }

    public void setCodeCreance(String codeCreance) {
        this.codeCreance = codeCreance;
    }

    public double getSoldeCreance() {
        return soldeCreance;
    }

    public void setSoldeCreance(double soldeCreance) {
        this.soldeCreance = soldeCreance;
    }

    public double getAvanceCreance() {
        return avanceCreance;
    }

    public void setAvanceCreance(double avanceCreance) {
        this.avanceCreance = avanceCreance;
    }

    public int getNbreJours() {
        return nbreJours;
    }

    public void setNbreJours(int nbreJours) {
        this.nbreJours = nbreJours;
    }

    public double getTotalCreance() {
        return totalCreance;
    }

    public void setTotalCreance(double totalCreance) {
        this.totalCreance = totalCreance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateCreance() {
        return dateCreance;
    }

    public void setDateCreance(Date dateCreance) {
        this.dateCreance = dateCreance;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<LigneCreance> getLcreances() {
        return lcreances;
    }

    public void setLcreances(List<LigneCreance> lcreances) {
        this.lcreances = lcreances;
    }
}
