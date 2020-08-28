package com.library.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "fournisseur")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Fournisseur implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String code;
	private String raisonSociale;
	private String prenom;
	private String nom;
	private String nomBank;
	private String numeroCompte;
	private String adresse;
	private String telephone;
	private String fax;
	private String email;
}

