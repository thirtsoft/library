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

	private String subject;
	private String message;

	public Fournisseur(Long id, String code, String raisonSociale, String prenom, String nom, String nomBank, String numeroCompte, String adresse, String telephone, String fax, String email) {
		this.id = id;
		this.code = code;
		this.raisonSociale = raisonSociale;
		this.prenom = prenom;
		this.nom = nom;
		this.nomBank = nomBank;
		this.numeroCompte = numeroCompte;
		this.adresse = adresse;
		this.telephone = telephone;
		this.fax = fax;
		this.email = email;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

