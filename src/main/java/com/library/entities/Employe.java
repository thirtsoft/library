package com.library.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "employe")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employe extends AbstractEntity {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String prenom;

    private String nom;

    @Column(length = 100, unique = true)
    private String cni;

    private String adresse;

    @Column(length = 100, unique = true)
    private String telephone;

    private String telephone2;

    @Column(length = 100, unique = true)
    private String email;


}

