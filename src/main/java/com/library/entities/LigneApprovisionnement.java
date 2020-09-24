package com.library.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "ligneApprovisionnement")

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LigneApprovisionnement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;
    private int quantite;
    private double prix;

    @ManyToOne
    @JoinColumn(name="Appro_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Approvisionnement approvisionnement;

    @ManyToOne
    @JoinColumn(name="prod_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Produit produit;

}
