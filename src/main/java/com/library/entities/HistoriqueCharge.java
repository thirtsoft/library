package com.library.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "historiqueCharge")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoriqueCharge implements Serializable {

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
    @JoinColumn(name = "chargeId")
    private Charge charge;

}
