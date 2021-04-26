package com.library.repository;

import com.library.entities.Scategorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScategorieRepository extends JpaRepository<Scategorie, Long> {

    Scategorie findByCode(String code);

    @Query("select p from Scategorie p where p.code like :code")
    List<Scategorie> findListScategorieByCode(@Param("code") String code);

    @Query("select p from Scategorie p where p.libelle like :libelle")
    Scategorie findByLibelle(@Param("libelle") String libelle);

    @Query("select p from Scategorie p where p.libelle like :libelle")
    List<Scategorie> findListScategorieByLibelle(@Param("libelle") String libelle);

    @Query("select p from Scategorie p where p.categorie.id =:cat")
    List<Scategorie> findScategorieByCateoryId(@Param("cat") Long catId);

}
