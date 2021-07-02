package com.library.repository;

import com.library.entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {

    Produit findByReference(String reference);

    @Query("select p from Produit p where p.designation like :des")
    Produit findByDesignation(@Param("des") String designation);

    @Query("select p from Produit p where p.prixAchat like :price")
    Produit findByPrixAchat(@Param("price") Double prixAchat);

    @Query("select p from Produit p where p.scategorie.id =:scat")
    List<Produit> findProductByScateoryId(@Param("scat") Long scatId);

    @Query("select (p.scategorie.libelle), count(p) from Produit p where (p.qtestock > p.stockInitial) group by(p.scategorie)")
    List<?> countNumberOfProduitWithStoc();

    @Query("select count(p) from Produit p where (p.qtestock  > p.stockInitial) ")
    Integer countNumbersOfProductsByStock();

    @Query("select count(p) from Produit p where (p.qtestock = p.stockInitial) ")
    Integer countNumbersOfProductsWhenQStockEqualStockInit();

    @Query("select count(p) from Produit p where (p.stockInitial > p.qtestock) ")
    Integer countNumbersOfProductsWhenQStockInfStockInit();

    @Query("select p from Produit p where p.designation like :des")
    List<Produit> findListProduitByDesignation(@Param("des") String designation);

    Produit findByBarCode(String barCode);

    Produit findByQrCode(String qrCode);


}
