package com.library.entities;

import java.io.Serializable;

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
@Table(name = "ligneCmdClient")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LigneCmdClient implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "Num_Commande", nullable = false)
	private CommandeClient commande;
	
	@ManyToOne
	@JoinColumn(name = "Ref_Produit", nullable = false)
	private Produit produit;
	
	private int quantite;
	
	private double prix;
}

