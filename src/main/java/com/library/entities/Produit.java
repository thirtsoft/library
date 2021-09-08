package com.library.entities;

import javax.persistence.*;


@Entity
@Table(name = "produit", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "reference"
        }),
        @UniqueConstraint(columnNames = {
                "barCode"
        }),
        @UniqueConstraint(columnNames = {
                "qrCode"
        })
})
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

    private int qtestock;

    private int stockInitial;

    private boolean promo;

    @Column(name = "barcode")
    private String barCode;

    @Column(name = "qrcode", unique = true)
    private String qrCode;

    @ManyToOne
    @JoinColumn(name = "scat_id")
    private Scategorie scategorie;

    public Produit() {
        super();
    }


    public Produit(Long id, String reference, String designation,
                   Double prixAchat, Double prixVente, Double prixDetail,
                   int qtestock, int stockInitial, boolean promo, Scategorie scategorie) {
        this.id = id;
        this.reference = reference;
        this.designation = designation;
        this.prixAchat = prixAchat;
        this.prixVente = prixVente;
        this.prixDetail = prixDetail;
        this.qtestock = qtestock;
        this.stockInitial = stockInitial;
        this.promo = promo;
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

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public Scategorie getScategorie() {
        return scategorie;
    }

    public void setScategorie(Scategorie scategorie) {
        this.scategorie = scategorie;
    }

}
