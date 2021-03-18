package com.library.repository;

import com.library.entities.LigneAvoir;
import com.library.entities.LigneCmdClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LigneAvoirRepository extends JpaRepository<LigneAvoir, Long> {

    List<LigneAvoir> findAllByNumero(long numero);

    @Modifying
    @Query("delete from LigneAvoir where numero = :numero")
    void deleteByNumero(@Param("numero") long numero);

    @Query("select p from LigneAvoir p where p.produit.id =:prod")
    List<LigneAvoir> ListLigneAvoirByProduitId(@Param("prod") Long prodId);

    @Query("select p from LigneAvoir p where p.avoir.id =:num")
    List<LigneAvoir> ListLigneAvoirByAvoirId(@Param("num") Long avoirId);

}
