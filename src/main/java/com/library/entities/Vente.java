package com.library.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "vente")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Vente implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String numeroVente;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateVente;


	@OneToMany(mappedBy = "vente")
	private List<LigneVente> ligneVentes = new ArrayList<>();

	private Double totalVente;

	private String status;
}

