package com.library.repository;

import com.library.entities.LigneApprovisionnement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LigneApprovisionnementRepository extends JpaRepository<LigneApprovisionnement, Long> {

    List<LigneApprovisionnement> findAllByNumero(long numero);

    @Modifying
    @Query("delete from LigneApprovisionnement where numero = :numero")
    void deleteByNumero(@Param("numero") long numero);

    @Query("select p from LigneApprovisionnement p where p.produit.id =:prod")
    List<LigneApprovisionnement> findListLigneApprovisionnementByProduitId(@Param("prod") Long prodId);

    @Query("select p from LigneApprovisionnement p where p.approvisionnement.id =:num")
    List<LigneApprovisionnement> findListLigneApprovisionnementByApprovisionnementId(@Param("num") Long approId);

    @Query("select p from LigneApprovisionnement p where p.approvisionnement.id =:id")
    Page<LigneApprovisionnement> findLigneApprovisionnementByApprovisionnementPageable(@Param("id") Long approId, Pageable pageable);

    @Query("select p from LigneApprovisionnement p where p.produit.id =:id")
    Page<LigneApprovisionnement> findLigneApprovisionnementByProduitPageable(@Param("id") Long prodId, Pageable pageable);

    @Query("select p from LigneApprovisionnement p")
    Page<LigneApprovisionnement> findAllLigneApprovisionnementByPageable(Pageable pageable);
}
