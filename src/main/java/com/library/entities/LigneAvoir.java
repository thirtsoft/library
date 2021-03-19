package com.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ligneAvoir")
public class LigneAvoir implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private long numero;
	private int quantite;
	private double prix;

	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name="avoir_id")
	@JsonIgnoreProperties(value = {"lavoirs"})
	private Avoir avoir;

	@ManyToOne
	@JoinColumn(name="prod_id")
	private Produit produit;

	public LigneAvoir() {
		super();
	}

	public LigneAvoir(Long id, long numero, int quantite, double prix, Avoir avoir, Produit produit) {
		this.id = id;
		this.numero = numero;
		this.quantite = quantite;
		this.prix = prix;
		this.avoir = avoir;
		this.produit = produit;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getNumero() {
		return numero;
	}

	public void setNumero(long numero) {
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

	public Avoir getAvoir() {
		return avoir;
	}

	public void setAvoir(Avoir avoir) {
		this.avoir = avoir;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}
}

