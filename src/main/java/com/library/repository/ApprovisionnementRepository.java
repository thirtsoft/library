package com.library.repository;

import com.library.entities.Approvisionnement;
import com.library.entities.CommandeClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ApprovisionnementRepository extends JpaRepository<Approvisionnement, Long> {
/*
    @Query("select p from Approvisionnement p where p.code like :code")
    public Approvisionnement findApprovisionnementByCode(@Param("code") String code);
*/
    @Query("select p from Approvisionnement p where p.code like :num")
    Approvisionnement findByCode(@Param("num") int code);
/*
    @Query("select c from Approvisionnement c where c.code like :code")
    public List<Approvisionnement> findListApprovisionnementByCode(@Param("code") String code);
*/

    @Query("select p from Approvisionnement p where p.fournisseur.id =:cl")
    public List<Approvisionnement> findListApprovisionnementByFournisseurId(@Param("cl") Long fourId);

    @Query("select p from Approvisionnement p where p.fournisseur.id =:id")
    public Page<Approvisionnement> findApprovisionnementByFournisseurId(@Param("id") Long fourId, Pageable pageable);

    @Query("select p from Approvisionnement p")
    public Page<Approvisionnement> findAllApprovisionnementByPageable(Pageable pageable);

    @Query("select p from Approvisionnement p where p.code like :mc")
    public Page<Approvisionnement> findApprovisionnementByKeyWord(@Param("mc") String mc, Pageable pageable);
}
