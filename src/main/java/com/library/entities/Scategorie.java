package com.library.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "sCategorie")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Scategorie extends AbstractEntity {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, unique = true)
    private String code;

    private String libelle;

    @ManyToOne
    @JoinColumn(name = "cat_id")
    private Category categorie;

}
