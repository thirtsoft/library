package com.library.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import javax.validation.Valid;
import java.io.Serializable;
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

   // private long numeroCommande;
    private Long numeroCommande;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "GMT")
    private Date dateCommande;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    //@JsonManagedReference
    //@JsonIgnore
//	@JsonIgnoreProperties(value = {"commande"})
    @OneToMany(mappedBy = "commande", fetch = FetchType.LAZY)
    @Valid
    private List<LigneCmdClient> lcomms = new ArrayList<>();

    private double totalCommande;

    private String status;

    public CommandeClient() {
        super();
    }

    public CommandeClient(Long id, Long numeroCommande, Date dateCommande, Client client, @Valid List<LigneCmdClient> lcomms, double totalCommande, String status) {
        this.id = id;
        this.numeroCommande = numeroCommande;
        this.dateCommande = dateCommande;
        this.client = client;
        this.lcomms = lcomms;
        this.totalCommande = totalCommande;
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
