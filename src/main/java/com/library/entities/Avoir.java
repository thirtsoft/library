package com.library.entities;

import java.io.Serializable;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Table(name = "avoir")

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Avoir implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String reference;
	private String libelle;
	private double soldeAvoir;
	private int nbreJours;

	@ManyToOne
	@JoinColumn(name="four_id")
	private Fournisseur fournisseur;

}
