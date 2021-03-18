package com.library.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "devis")
public class Devis implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long numeroDevis;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "GMT")
    private Date dateDevis;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "devis", fetch = FetchType.LAZY)
    @Valid
    private List<LigneDevis> ldevis = new ArrayList<>();

    private double totalDevis;

    private String status;

    public Devis() {

    }

    public Devis(Long id, long numeroDevis, Date dateDevis, Client client, @Valid List<LigneDevis> ldevis, double totalDevis, String status) {
        this.id = id;
        this.numeroDevis = numeroDevis;
        this.dateDevis = dateDevis;
        this.client = client;
        this.ldevis = ldevis;
        this.totalDevis = totalDevis;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getNumeroDevis() {
        return numeroDevis;
    }

    public void setNumeroDevis(long numeroDevis) {
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

    @Override
    public String toString() {
        return "Devis{" +
                "id=" + id +
                ", numeroDevis=" + numeroDevis +
                ", dateDevis=" + dateDevis +
                ", client=" + client +
                ", ldevis=" + ldevis +
                ", totalDevis=" + totalDevis +
                ", status='" + status + '\'' +
                '}';
    }

}
