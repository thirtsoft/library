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
import com.fasterxml.jackson.annotation.JsonProperty;

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
	private String numero;
	private int quantite;
	private double prix;
	
	@ManyToOne
<<<<<<< HEAD
	@JoinColumn(name="cmdClient_id", nullable = false)
	private CommandeClient commande;
		
	@ManyToOne
	@JoinColumn(name="prod_id", nullable = false)
=======
	@JoinColumn(name="cmdClient_id")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private CommandeClient commande;
		
	@ManyToOne
	@JoinColumn(name="prod_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
>>>>>>> 962d992518874a2014c813f38e02d77021502842
	private Produit produit;


	
	
}

