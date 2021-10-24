package com.library.repository;

import com.library.entities.Vente;
import com.library.entities.Versement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VersementRepository extends JpaRepository<Versement, Long> {

    Versement findByNumVersement(String numVersement);

    Versement findByNumeroRecu(String numeroRecu);

    @Query("select p from Versement p where p.numVersement like :num")
    List<Versement> findListVersementByNumVersement(@Param("num") String numVersement);

    @Query("select p from Versement p where p.nature like :nat")
    Versement findByNature(@Param("nat") String nature);

    @Query("select p from Versement p where p.nature like :nat")
    List<Versement> findListVersementNature(@Param("nat") String nature);

    @Query("select v from Versement v where v.employe.id =:emp")
    List<Versement> findVersementByEmployeId(@Param("emp") Long empId);

    List<Versement> findByOrderByIdDesc();

}
