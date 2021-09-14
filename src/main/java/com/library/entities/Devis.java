package com.library.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "devis")
public class Devis extends AbstractEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long numeroDevis;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "GMT")
    private Date dateDevis;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private Utilisateur utilisateur;

    @OneToMany(mappedBy = "devis", fetch = FetchType.LAZY)
    @Valid
    private List<LigneDevis> ldevis = new ArrayList<>();

    private double totalDevis;

    private String status;

    private String observation;

    public Devis() {

    }

    public Devis(Long id, Long numeroDevis, Date dateDevis,
                 Client client, @Valid List<LigneDevis> ldevis,
                 double totalDevis, String observation) {
        this.id = id;
        this.numeroDevis = numeroDevis;
        this.dateDevis = dateDevis;
        this.client = client;
        this.ldevis = ldevis;
        this.totalDevis = totalDevis;
        this.observation = observation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroDevis() {
        return numeroDevis;
    }

    public void setNumeroDevis(Long numeroDevis) {
        this.numeroDevis = numeroDevis;
    }

    public Date getDateDevis() {
        return dateDevis;
    }

    public void setDateDevis(Date dateDevis) {
        this.dateDevis = dateDevis;
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

    public List<LigneDevis> getLdevis() {
        return ldevis;
    }

    public void setLdevis(List<LigneDevis> ldevis) {
        this.ldevis = ldevis;
    }

    public double getTotalDevis() {
        return totalDevis;
    }

    public void setTotalDevis(double totalDevis) {
        this.totalDevis = totalDevis;
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

    @Override
    public String toString() {
        return "Devis{" +
                "numeroDevis=" + numeroDevis +
                ", dateDevis=" + dateDevis +
                ", client=" + client +
                ", ldevis=" + ldevis +
                ", totalDevis=" + totalDevis +
                ", observation='" + observation + '\'' +
                '}';
    }

}
