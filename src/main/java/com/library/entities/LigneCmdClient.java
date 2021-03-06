package com.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "ligneCmdClient")
public class LigneCmdClient extends AbstractEntity {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long numero;

    private int quantite;

    private double prix;

    private double prixCommande;

    @ManyToOne(fetch = FetchType.LAZY)
    //@JsonBackReference
    @JoinColumn(name = "cmd_id")
//	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    // @JsonIgnore
    @JsonIgnoreProperties(value = {"lcomms"})
    private CommandeClient commande;

    @ManyToOne
    @JoinColumn(name = "prod_id")
    private Produit produit;

    public LigneCmdClient() {
        super();
    }

    public LigneCmdClient(Long id, Long numero, int quantite, double prix, double prixCommande, CommandeClient commande, Produit produit) {
        this.id = id;
        this.numero = numero;
        this.quantite = quantite;
        this.prix = prix;
        this.prixCommande = prixCommande;
        this.commande = commande;
        this.produit = produit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public double getPrixCommande() {
        return prixCommande;
    }

    public void setPrixCommande(double prixCommande) {
        this.prixCommande = prixCommande;
    }

    public CommandeClient getCommande() {
        return commande;
    }

    public void setCommande(CommandeClient commande) {
        this.commande = commande;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

}

