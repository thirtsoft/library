package com.library.repository;

import com.library.entities.Avoir;
import com.library.entities.Creance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvoirRepository extends JpaRepository<Avoir, Long> {

    @Query("select p from Avoir p where p.reference like :ref")
    public Avoir findByReference(@Param("ref") String reference);

    @Query("select p from Avoir p where p.reference like :ref")
    public List<Avoir> findListAvoirByReference(@Param("ref") String reference);


    @Query("select p from Avoir p where p.libelle like :lib")
    public Avoir findByLibelle(@Param("lib") String libelle);

    @Query("select p from Avoir p where p.libelle like :lib")
    public List<Avoir> findListAvoirByLibelle(@Param("lib") String libelle);

    @Query("select p from Avoir p where p.fournisseur.id =:four")
    public List<Avoir> findLitAvoirByFournisseurId(@Param("four") Long fourId);

    @Query("select p from Avoir p where p.fournisseur.id =:id")
    public Page<Avoir> findAvoirByFournisseurByPageable(@Param("id") Long fourId, Pageable pageable);

    @Query("select p from Avoir p")
    public Page<Avoir> findAllAvoirsByPageable(Pageable pageable);

    @Query("select p from Avoir p where p.libelle like :mc")
    public Page<Avoir> findAvoirsByKeyWord(@Param("mc") String mc, Pageable pageable);

}
