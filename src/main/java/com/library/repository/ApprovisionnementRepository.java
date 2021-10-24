package com.library.repository;

import com.library.entities.Approvisionnement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprovisionnementRepository extends JpaRepository<Approvisionnement, Long> {

    @Query("select p from Approvisionnement p where p.code like :num")
    Approvisionnement findByCode(@Param("num") Long code);

    @Query("select p from Approvisionnement p where p.fournisseur.id =:cl")
    List<Approvisionnement> findListApprovisionnementByFournisseurId(@Param("cl") Long fourId);

    List<Approvisionnement> findByOrderByIdDesc();

}
