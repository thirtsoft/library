package com.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ligneApprovisionnement")
public class LigneApprovisionnement extends AbstractEntity {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  //  private long numero;
    private Long numero;

    private int quantite;

    private double prix;

    private double prixAppro;

    // @ManyToOne
    //@JoinColumn(name="Appro_id")
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Appro_id")
    @JsonIgnoreProperties(value = {"ligneApprovisionnements"})
    private Approvisionnement approvisionnement;

    @ManyToOne
    @JoinColumn(name = "prod_id")
    //  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Produit produit;

    public LigneApprovisionnement() {
        super();
    }

    public LigneApprovisionnement(Long id, Long numero, int quantite, double prix, double prixAppro, Approvisionnement approvisionnement, Produit produit) {
        this.id = id;
        this.numero = numero;
        this.quantite = quantite;
        this.prix = prix;
        this.approvisionnement = approvisionnement;
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

    public double getPrixAppro() {
        return prixAppro;
    }

    public void setPrixAppro(double prixAppro) {
        this.prixAppro = prixAppro;
    }

    public Approvisionnement getApprovisionnement() {
        return approvisionnement;
    }

    public void setApprovisionnement(Approvisionnement approvisionnement) {
        this.approvisionnement = approvisionnement;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

}
