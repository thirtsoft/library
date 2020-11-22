package com.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ligneVente")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@ToString
public class LigneVente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int numero;
    private int quantite;
    private double prixVente;

    @ManyToOne
    @JoinColumn(name="vente_id")
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonIgnoreProperties(value = {"ligneVentes"})
    private Vente vente;

    @ManyToOne
    @JoinColumn(name="prod_id")
   // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Produit produit;

    public LigneVente() {
    }

    public LigneVente(Long id, int numero, int quantite, double prixVente, Vente vente, Produit produit) {
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

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
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
