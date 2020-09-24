package com.library.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ligneVente")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LigneVente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;
    private int quantite;
    private double prixVente;

    @ManyToOne
    @JoinColumn(name="vente_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Vente vente;

    @ManyToOne
    @JoinColumn(name="prod_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Produit produit;
}
