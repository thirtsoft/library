package com.library.entities;

import javax.persistence.*;


@Entity
@Table(name = "produit")
public class Produit extends AbstractEntity {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String reference;

    private String designation;

    private Double prixAchat;

    private Double prixVente;

    private Double prixDetail;

    private Double tva;

    private int qtestock;

    private int stockInitial;

    private boolean promo;

    private String photo;

    @ManyToOne
    @JoinColumn(name = "scat_id")
    private Scategorie scategorie;

/*	@OneToMany(mappedBy = "produit", fetch=FetchType.LAZY)
	private Collection<LigneCmdClient> lcomms;
	*/

    public Produit() {
        super();
    }


    public Produit(Long id, String reference, String designation, Double prixAchat, Double prixVente, Double prixDetail, Double tva, int qtestock, int stockInitial, boolean promo, String photo, Scategorie scategorie) {
        this.id = id;
        this.reference = reference;
        this.designation = designation;
        this.prixAchat = prixAchat;
        this.prixVente = prixVente;
        this.prixDetail = prixDetail;
        this.tva = tva;
        this.qtestock = qtestock;
        this.stockInitial = stockInitial;
        this.promo = promo;
        this.photo = photo;
        this.scategorie = scategorie;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Double getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(Double prixAchat) {
        this.prixAchat = prixAchat;
    }

    public Double getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(Double prixVente) {
        this.prixVente = prixVente;
    }

    public Double getPrixDetail() {
        return prixDetail;
    }

    public void setPrixDetail(Double prixDetail) {
        this.prixDetail = prixDetail;
    }

    public Double getTva() {
        return tva;
    }

    public void setTva(Double tva) {
        this.tva = tva;
    }

    public int getQtestock() {
        return qtestock;
    }

    public void setQtestock(int qtestock) {
        this.qtestock = qtestock;
    }

    public int getStockInitial() {
        return stockInitial;
    }

    public void setStockInitial(int stockInitial) {
        this.stockInitial = stockInitial;
    }

    public boolean isPromo() {
        return promo;
    }

    public void setPromo(boolean promo) {
        this.promo = promo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Scategorie getScategorie() {
        return scategorie;
    }

    public void setScategorie(Scategorie scategorie) {
        this.scategorie = scategorie;
    }

}
