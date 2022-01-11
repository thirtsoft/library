package com.library.repository;

import com.library.entities.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {

    Fournisseur findByCode(String code);

    Fournisseur findByEmail(String email);

    @Query("select count(f) from Fournisseur f ")
    Integer countNumberOfFournisseurs();

    @Query("select c from Fournisseur c where c.raisonSociale like :raison")
    Fournisseur findByRaisonSociale(@Param("raison") String raisonSociale);

    @Query("select c from Fournisseur c where c.raisonSociale like :r")
    List<Fournisseur> ListFournisseurByRaisonSociale(@Param("r") String raisonSociale);

    @Query("select c from Fournisseur c where c.code like :c")
    List<Fournisseur> ListFournisseurByCode(@Param("c") String code);

    List<Fournisseur> findByOrderByIdDesc();

}
