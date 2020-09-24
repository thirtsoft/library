package com.library.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "commandeClient")
@Data
@AllArgsConstructor
@NoArgsConstructor
//@ToString
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
<<<<<<< HEAD
	@JoinColumn(name="client_id", nullable = false)
=======
	@JoinColumn(name="client_id")
>>>>>>> 962d992518874a2014c813f38e02d77021502842
	private Client client;
	
	@OneToMany(mappedBy = "commande") 
	private List<LigneCmdClient> ligneCmdClients = new ArrayList<>();
	
	private Double totalCommande;
	
	private String status;
}
