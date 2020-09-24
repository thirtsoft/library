package com.library.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "approvisionnement")

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Approvisionnement {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private double totalAppro;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateApprovisionnement;

    private String status;

    @ManyToOne
    @JoinColumn(name="four_id")
    private Fournisseur fournisseur;

    @OneToMany(mappedBy = "approvisionnement")
    private List<LigneApprovisionnement> ligneApprovisionnements = new ArrayList<>();



}
