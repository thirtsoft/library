package com.library.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "client")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client extends AbstractEntity {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String codeClient;

    private String raisonSocial;

    private String adresse;

    @Column(unique = true)
    private String telephone;

    @Column(name = "mobile", unique = true)
    private String mobile;

    @Column(unique = true)
    private String email;

    private String subject;

    private String message;

    public Client(Long id, String codeClient, String raisonSocial,
                  String adresse, String telephone,
                  String mobile, String email) {
        this.id = id;
        this.codeClient = codeClient;
        this.raisonSocial = raisonSocial;
        this.adresse = adresse;
        this.telephone = telephone;
        this.mobile = mobile;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
