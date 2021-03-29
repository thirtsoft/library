package com.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "ligneDevis")
public class LigneDevis extends AbstractEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // private long numero;
    private Long numero;

    private int quantite;

    private double prix;

    private double prixDevis;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dev_id")
    @JsonIgnoreProperties(value = {"ldevis"})
    private Devis devis;

    @ManyToOne
    @JoinColumn(name = "prod_id")
    private Produit produit;

    public LigneDevis() {
    }

    public LigneDevis(Long id, Long numero, int quantite, double prix, double prixDevis, Devis devis, Produit produit) {
        this.id = id;
        this.numero = numero;
        this.quantite = quantite;
        this.prix = prix;
        this.prixDevis = prixDevis;
        this.devis = devis;
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

    public double getPrixDevis() {
        return prixDevis;
    }

    public void setPrixDevis(double prixDevis) {
        this.prixDevis = prixDevis;
    }

    public Devis getDevis() {
        return devis;
    }

    public void setDevis(Devis devis) {
        this.devis = devis;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    @Override
    public String toString() {
        return "LigneDevis{" +
                "numero=" + numero +
                ", quantite=" + quantite +
                ", prix=" + prix +
                ", prixDevis=" + prixDevis +
                ", devis=" + devis +
                ", produit=" + produit +
                '}';
    }

}
