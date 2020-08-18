package com.library.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Table(name = "produit")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Produit implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String reference;
	private String designation;
	private Double prixAchat;
	private Double prixVente;
	private Double tva;
	private int stockInitial;
	private boolean promo; 
	private String photo;
	private Date add_date;
	@ManyToOne
	//@JoinColumn(name="cat_id", insertable = false, updatable = false)
	@JoinColumn(name="cat_id", nullable = false)
	private Category categorie;
	public Produit(String reference, String designation, Double prixAchat, Double prixVente, Double tva,
			int stockInitial, boolean promo, String photo, Date add_date, Category categorie) {
		this.reference = reference;
		this.designation = designation;
		this.prixAchat = prixAchat;
		this.prixVente = prixVente;
		this.tva = tva;
		this.stockInitial = stockInitial;
		this.promo = promo;
		this.photo = photo;
		this.add_date = add_date;
		this.categorie = categorie;
	}
	
	

}
