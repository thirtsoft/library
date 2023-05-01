package com.library.repository;

import com.library.entities.Avoir;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvoirRepository extends JpaRepository<Avoir, Long> {

    @Query("select p from Avoir p where p.reference like :ref")
    Avoir findByReference(@Param("ref") Long reference);

    @Query("select p from Avoir p where p.reference like :ref")
    List<Avoir> findListAvoirByReference(@Param("ref") Long reference);

    @Query("select p from Avoir p where p.libelle like :lib")
    Avoir findByLibelle(@Param("lib") String libelle);

    @Query("select p from Avoir p where p.libelle like :lib")
    List<Avoir> findListAvoirByLibelle(@Param("lib") String libelle);

    @Query("select p from Avoir p where p.fournisseur.id =:four")
    List<Avoir> findLitAvoirByFournisseurId(@Param("four") Long fourId);

    @Query("select p from Avoir p where p.status like :status")
    Avoir findByStatus(@Param("status") String status);

    @Query("select c from Avoir c where c.status like :status")
    List<Avoir> ListAvoirByStatus(@Param("status") String status);

    List<Avoir> findByOrderByIdDesc();

}
