package com.library.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ligneVente")
public class LigneVente extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // private long numero;
    private Long numero;

    private int quantite;

    private double prixVente;

    @ManyToOne
    @JoinColumn(name = "vente_id")
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonIgnoreProperties(value = {"ligneVentes"})
    private Vente vente;

    @ManyToOne
    @JoinColumn(name = "prod_id")
    // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Produit produit;

    public LigneVente() {
    }

    public LigneVente(Long id, Long numero, int quantite, double prixVente, Vente vente, Produit produit) {
        this.id = id;
        this.numero = numero;
        this.quantite = quantite;
        this.prixVente = prixVente;
        this.vente = vente;
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

    public double getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(double prixVente) {
        this.prixVente = prixVente;
    }

    public Vente getVente() {
        return vente;
    }

    public void setVente(Vente vente) {
        this.vente = vente;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }
}
