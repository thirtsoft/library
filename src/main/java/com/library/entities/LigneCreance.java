package com.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ligneCreance")
public class LigneCreance implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
//	private long numero;
	private Long numero;
	private int quantite;
	private double prix;

	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name="creance_id")
	@JsonIgnoreProperties(value = {"lcreances"})
	private Creance creance;

	@ManyToOne
	@JoinColumn(name="prod_id")
	private Produit produit;

	public LigneCreance() {
		super();
	}

	public LigneCreance(Long id, Long numero, int quantite, double prix, Creance creance, Produit produit) {
		this.id = id;
		this.numero = numero;
		this.quantite = quantite;
		this.prix = prix;
		this.creance = creance;
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

	public Creance getCreance() {
		return creance;
	}

	public void setCreance(Creance creance) {
		this.creance = creance;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

}


