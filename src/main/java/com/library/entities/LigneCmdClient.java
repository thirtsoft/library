package com.library.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Entity
@Table(name = "ligneCmdClient")
/*@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString*/
public class LigneCmdClient implements Serializable {
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
	private double prixCommande;

	@ManyToOne (fetch = FetchType.LAZY)
	//@JsonBackReference
	@JoinColumn(name="cmd_id")
//	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
   // @JsonIgnore
	@JsonIgnoreProperties(value = {"lcomms"})
	private CommandeClient commande;
		
	@ManyToOne
	@JoinColumn(name="prod_id")
   // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Produit produit;

	public LigneCmdClient() {
		super();
	}

	public LigneCmdClient(Long id, long numero, int quantite, double prix, double prixCommande, CommandeClient commande, Produit produit) {
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

