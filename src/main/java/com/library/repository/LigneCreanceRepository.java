package com.library.repository;

import com.library.entities.LigneCreance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LigneCreanceRepository extends JpaRepository<LigneCreance, Long> {

    List<LigneCreance> findAllByNumero(Long numero);
    //  List<LigneCreance> findAllByNumero(long numero);

    @Modifying
    @Query("delete from LigneCreance where numero = :numero")
    void deleteByNumero(@Param("numero") Long numero);
    // void deleteByNumero(@Param("numero") long numero);

    @Query("select p from LigneCreance p where p.produit.id =:prod")
    List<LigneCreance> ListLigneCreanceByProduitId(@Param("prod") Long prodId);

    @Query("select p from LigneCreance p where p.creance.id =:num")
    List<LigneCreance> ListLigneCreanceByCreanceId(@Param("num") Long creanceId);

    List<LigneCreance> findByOrderByIdDesc();

}
