package com.library.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "historiqueCreance")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoriqueCreance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date createdDate;

    private String status;

    private String action;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "creanceId")
    private Creance creance;

}
