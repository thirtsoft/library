package com.library.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "commandeClient")
public class CommandeClient extends AbstractEntity {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long numeroCommande;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "GMT")
    private Date dateCommande;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private Utilisateur utilisateur;

    @OneToMany(mappedBy = "commande", fetch = FetchType.LAZY)
    @Valid
    private List<LigneCmdClient> lcomms = new ArrayList<>();

    private double totalCommande;

    private String typeReglement;

    private double montantReglement;

    private String status;

    public CommandeClient() {
        super();
    }

    public CommandeClient(Long id, Long numeroCommande, Date dateCommande,
                          Client client, @Valid List<LigneCmdClient> lcomms,
                          double totalCommande, String typeReglement,
                          double montantReglement, String status) {
        this.id = id;
        this.numeroCommande = numeroCommande;
        this.dateCommande = dateCommande;
        this.client = client;
        this.lcomms = lcomms;
        this.totalCommande = totalCommande;
        this.typeReglement = typeReglement;
        this.montantReglement = montantReglement;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroCommande() {
        return numeroCommande;
    }

    public void setNumeroCommande(Long numeroCommande) {
        this.numeroCommande = numeroCommande;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
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

    @JsonGetter
    public List<LigneCmdClient> getLcomms() {
        return lcomms;
    }

    public void setLcomms(List<LigneCmdClient> lcomms) {
        this.lcomms = lcomms;
    }

    public double getTotalCommande() {
        return totalCommande;
    }

    public void setTotalCommande(double totalCommande) {
        this.totalCommande = totalCommande;
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

    @Override
    public String toString() {
        return "CommandeClient{" +
                "numeroCommande=" + numeroCommande +
                ", dateCommande=" + dateCommande +
                ", client=" + client +
                ", lcomms=" + lcomms +
                ", totalCommande=" + totalCommande +
                ", status='" + status + '\'' +
                '}';
    }

}
