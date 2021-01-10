package com.library.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "versement")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Versement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 100, unique = true)
	private String numVersement;
	private String nature;
	@Column(length = 100, unique = true)
	private String numeroRecu;
	private String nomBank;
	private Double montantVersement;
	private String motif;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT")
	private Date dateVersement;

	@ManyToOne
	@JoinColumn(name="empId", nullable = false)
	private Employe employe;
}

