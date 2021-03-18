package com.library.entities;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "email")
public class Email implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
  //  private String name;
    private static final String name = "Librairie Al-AMINE";
    private static final String from = "thirdiallo@gmail.com";
    private String recever;
    private String subject;
    private String message;

    @ManyToOne
    private Fournisseur fournisseur;

    public Email(String recever, String subject, String message) {
        this.recever = this.fournisseur.getEmail();
        this.subject = subject;
        this.message = message;
    }

    public Email() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecever() {
        return recever;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public String getName() {
        return name;
    }


    public String getFrom() {
        return from;
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

    @Override
    public String toString() {
        return "Email{" +
                "recever='" + recever + '\'' +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

}
