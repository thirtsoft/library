package com.library.repository;

import com.library.entities.LigneDevis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LigneDevisRepository extends JpaRepository<LigneDevis, Long> {

    // List<LigneDevis> findAllByNumero(long numero);
    List<LigneDevis> findAllByNumero(Long numero);

    @Modifying
    @Query("delete from LigneDevis where numero = :numero")
    void deleteByNumero(@Param("numero") Long numero);
    // void deleteByNumero(@Param("numero") long numero);

    @Query("select p from LigneDevis p where p.produit.id =:prod")
    List<LigneDevis> ListLigneDevisByProduitId(@Param("prod") Long prodId);

    @Query("select p from LigneDevis p where p.devis.id =:num")
    List<LigneDevis> ListLigneDevisByDevisId(@Param("num") Long devId);

}
