package com.library.entities;

import java.io.Serializable;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "creance")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Creance implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private double soldeCreance;
	private int nbreJours;

	@ManyToOne
	@JoinColumn(name="client_id", nullable = false)
	private Client client;

}
