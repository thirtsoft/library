package com.library.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "contrat")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Contrat implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 100, unique = true)
	private String reference;
	private String nature;
	private double montantContrat;
	private String description;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT")
	private Date dateDebutContrat;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT")
	private Date dateFinContrat;
	private String fileName;
	private String fileContrat;

	@Lob
	private byte[] content;

	@ManyToOne
	@JoinColumn(name = "client_id", nullable = false)
	private Client client;

}
