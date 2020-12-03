package com.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ligneCmdClient")
/*@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString*/
public class LigneAvoir implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int numero;
	private int quantite;
	private double prix;

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

	public LigneAvoir() {
		super();
	}

	public LigneAvoir(Long id, int numero, int quantite, double prix, CommandeClient commande, Produit produit) {
		this.id = id;
		this.numero = numero;
		this.quantite = quantite;
		this.prix = prix;
		this.commande = commande;
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

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
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

