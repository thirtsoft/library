package com.library.repository;

import com.library.entities.LigneApprovisionnement;
import com.library.entities.LigneCmdClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LigneApprovisionnementRepository extends JpaRepository<LigneApprovisionnement, Long> {

    @Query("select p from LigneApprovisionnement p where p.produit.id =:prod")
    public List<LigneApprovisionnement> findListLigneApprovisionnementByProduitId(@Param("prod") Long prodId);

    @Query("select p from LigneApprovisionnement p where p.approvisionnement.id =:num")
    public List<LigneApprovisionnement> findListLigneApprovisionnementByApprovisionnementId(@Param("num") Long approId);

    @Query("select p from LigneApprovisionnement p where p.approvisionnement.id =:id")
    public Page<LigneApprovisionnement> findLigneApprovisionnementByApprovisionnementPageable(@Param("id") Long approId, Pageable pageable);

    @Query("select p from LigneApprovisionnement p where p.produit.id =:id")
    public Page<LigneApprovisionnement> findLigneApprovisionnementByProduitPageable(@Param("id") Long prodId, Pageable pageable);

    @Query("select p from LigneApprovisionnement p")
    public Page<LigneApprovisionnement> findAllLigneApprovisionnementByPageable(Pageable pageable);
}
