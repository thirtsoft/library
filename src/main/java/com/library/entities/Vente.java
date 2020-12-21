package com.library.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "vente")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@ToString
public class Vente implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int numeroVente;

	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "GMT")
	private Date dateVente;


	@OneToMany(mappedBy = "vente", fetch = FetchType.LAZY)
	@Valid
	private List<LigneVente> ligneVentes = new ArrayList<>();

	private double totalVente;

	private String status;

	public Vente() {
	}

	public Vente(Long id, int numeroVente, Date dateVente, @Valid List<LigneVente> ligneVentes, double totalVente, String status) {
		this.id = id;
		this.numeroVente = numeroVente;
		this.dateVente = dateVente;
		this.ligneVentes = ligneVentes;
		this.totalVente = totalVente;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getNumeroVente() {
		return numeroVente;
	}

	public void setNumeroVente(int numeroVente) {
		this.numeroVente = numeroVente;
	}

	public Date getDateVente() {
		return dateVente;
	}

	public void setDateVente(Date dateVente) {
		this.dateVente = dateVente;
	}

	public List<LigneVente> getLigneVentes() {
		return ligneVentes;
	}

	public void setLigneVentes(List<LigneVente> ligneVentes) {
		this.ligneVentes = ligneVentes;
	}

	public double getTotalVente() {
		return totalVente;
	}

	public void setTotalVente(double totalVente) {
		this.totalVente = totalVente;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}

