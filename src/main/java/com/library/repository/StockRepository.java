package com.library.repository;

import com.library.entities.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    @Query("select p from Stock p where p.quantite like :qte")
    Stock findByQuantite(@Param("qte") int quantite);

    @Query("select p from Stock p where p.produit.id =:prod")
    Stock findStockByProductId(@Param("prod") Long prodId);

    @Query("select p from Stock p where p.produit.id =:prod")
    List<Stock> findListStockByProductId(@Param("prod") Long prodId);

    @Query("select p from Stock p where p.produit.id =:id")
    Page<Stock> findStockByProduitIdByPageable(@Param("id") Long prodId, Pageable pageable);

    @Query("select p from Stock p")
    Page<Stock> findAllStocksByPageable(Pageable pageable);

}
