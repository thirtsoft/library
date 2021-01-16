package com.library.entities;

import java.io.Serializable;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "client")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String codeClient;
	private String raisonSocial;
	private String chefService;
	private String adresse;
	@Column(unique = true)
	private String telephone;
	@Column(unique = true)
	private String email;

	private String subject;
	private String message;

	public Client(Long id, String codeClient, String raisonSocial, String chefService, String adresse, String telephone, String email) {
		this.id = id;
		this.codeClient = codeClient;
		this.raisonSocial = raisonSocial;
		this.chefService = chefService;
		this.adresse = adresse;
		this.telephone = telephone;
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
