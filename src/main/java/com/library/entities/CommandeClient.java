package com.library.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "commandeClient")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommandeClient implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String numCommande;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateCommande;
	
	@ManyToOne
	@JoinColumn(name = "code_client", nullable = false)
	private Client client;
	
	@OneToMany(mappedBy = "commande")
	private Collection<LigneCmdClient> ligneCmdClients;
	
	private Double totalCommande;
	
	private String status;
}
