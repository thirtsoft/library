package com.library.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "contrat")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Contrat extends AbstractEntity {
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

    @Column(name = "dateDebutContrat")
    private Date dateDebutContrat;

    @Column(name = "dateFinContrat")
    private Date dateFinContrat;

    private String fileContrat;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

}
