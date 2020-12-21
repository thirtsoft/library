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
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "creance",
uniqueConstraints = {
@UniqueConstraint(columnNames = "codeCreance")
})
/*
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
*/
public class Creance implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int reference;
    private String codeCreance;
	private String libelle;
	private double soldeCreance;
	private int nbreJours;
	private double totalCreance;
	private String status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "GMT")
	private Date dateCreance;

	@ManyToOne
	@JoinColumn(name="client_id")
	private Client client;

	@OneToMany(mappedBy = "creance", fetch = FetchType.LAZY)
	@Valid
	private List<LigneCreance> lcreances = new ArrayList<>();

	public Creance() {
		super();
	}

	public Creance(Long id, int reference,String codeCreance, String libelle, double soldeCreance, int nbreJours, double totalCreance, String status, Date dateCreance, Client client, @Valid List<LigneCreance> lcreances) {
		this.id = id;
		this.reference = reference;
		this.libelle = libelle;
		this.codeCreance = codeCreance;
		this.soldeCreance = soldeCreance;
		this.nbreJours = nbreJours;
		this.totalCreance = totalCreance;
		this.status = status;
		this.dateCreance = dateCreance;
		this.client = client;
		this.lcreances = lcreances;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getReference() {
		return reference;
	}

	public void setReference(int reference) {
		this.reference = reference;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

    public String getCodeCreance() {
        return codeCreance;
    }

    public void setCodeCreance(String codeCreance) {
        this.codeCreance = codeCreance;
    }

    public double getSoldeCreance() {
		return soldeCreance;
	}

	public void setSoldeCreance(double soldeCreance) {
		this.soldeCreance = soldeCreance;
	}

	public int getNbreJours() {
		return nbreJours;
	}

	public void setNbreJours(int nbreJours) {
		this.nbreJours = nbreJours;
	}

	public double getTotalCreance() {
		return totalCreance;
	}

	public void setTotalCreance(double totalCreance) {
		this.totalCreance = totalCreance;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDateCreance() {
		return dateCreance;
	}

	public void setDateCreance(Date dateCreance) {
		this.dateCreance = dateCreance;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<LigneCreance> getLcreances() {
		return lcreances;
	}

	public void setLcreances(List<LigneCreance> lcreances) {
		this.lcreances = lcreances;
	}
}
