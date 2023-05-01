package com.library.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;


@Entity
@Table(name = "produit", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "reference"
        }),
        @UniqueConstraint(columnNames = {
                "barCode"
        })
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Produit extends AbstractEntity {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "barcode")
    private String barCode;

  //  @Column(unique = true)
    private String reference;

    private String designation;

    private Double prixAchat;

    private Double prixVente;

    private Double prixDetail;

    private int qtestock;

    private int stockInitial;

    //  private boolean promo;

  /*  @Column(name = "qrcode", unique = true)
    private String qrCode;*/

    @ManyToOne
    @JoinColumn(name = "scat_id")
    private Scategorie scategorie;

    public Produit(Long id, String reference, String designation,
                   Double prixAchat, Double prixVente, Double prixDetail,
                   int qtestock, int stockInitial, Scategorie scategorie) {
        this.id = id;
        this.reference = reference;
        this.designation = designation;
        this.prixAchat = prixAchat;
        this.prixVente = prixVente;
        this.prixDetail = prixDetail;
        this.qtestock = qtestock;
        this.stockInitial = stockInitial;
        this.scategorie = scategorie;
    }

}
