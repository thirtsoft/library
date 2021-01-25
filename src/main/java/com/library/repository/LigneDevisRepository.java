package com.library.repository;

import com.library.entities.LigneCmdClient;
import com.library.entities.LigneDevis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LigneDevisRepository extends JpaRepository<LigneDevis, Long> {

    List<LigneDevis> findAllByNumero(int numero);

    @Modifying
    @Query("delete from LigneDevis where numero = :numero")
    void deleteByNumero(@Param("numero") int numero);

    @Query("select p from LigneDevis p where p.produit.id =:prod")
    List<LigneDevis> ListLigneDevisByProduitId(@Param("prod") Long prodId);

    @Query("select p from LigneDevis p where p.devis.id =:num")
    List<LigneDevis> ListLigneDevisByDevisId(@Param("num") Long devId);

}
