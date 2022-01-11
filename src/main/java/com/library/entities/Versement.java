package com.library.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "versement")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Versement extends AbstractEntity {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, unique = true)
    private String numVersement;

    @Column(length = 100, unique = true)
    private String numeroRecu;

    private Double montantVersement;

    private String motif;

    private String fileVersement;

    @Column(name = "dateVersement")
    private Date dateVersement;

    @ManyToOne
    @JoinColumn(name = "empId", nullable = false)
    private Employe employe;

}

