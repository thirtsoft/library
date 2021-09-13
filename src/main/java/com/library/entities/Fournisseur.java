package com.library.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "fournisseur")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fournisseur extends AbstractEntity {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, unique = true)
    private String code;

    private String raisonSociale;

    private String prenom;

    private String nom;

    @Column(length = 100, unique = true)
    private String nomBank;

    private String numeroCompte;

    private String adresse;

    private String telephone;

    @Column(name = "mobile")
    private String mobile;

    private String fax;

    @Column(length = 100, unique = true)
    private String email;

    private String subject;

    private String message;

    public Fournisseur(Long id, String code, String raisonSociale,
                       String prenom, String nom, String nomBank,
                       String numeroCompte, String adresse,
                       String telephone, String mobile,
                       String fax, String email) {
        this.id = id;
        this.code = code;
        this.raisonSociale = raisonSociale;
        this.prenom = prenom;
        this.nom = nom;
        this.nomBank = nomBank;
        this.numeroCompte = numeroCompte;
        this.adresse = adresse;
        this.telephone = telephone;
        this.mobile = mobile;
        this.fax = fax;
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

