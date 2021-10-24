package com.library.repository;

import com.library.entities.LigneDevis;
import com.library.entities.LigneVente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LigneVenteRepository extends JpaRepository<LigneVente, Long> {

    //  List<LigneVente> findAllByNumero(long numero);
    List<LigneVente> findAllByNumero(Long numero);

    Optional<LigneVente> findByCode(String code);

    List<LigneVente> findAllByCode(String code);

    @Modifying
    @Query("delete from LigneVente where numero = :numero")
    void deleteByNumero(@Param("numero") Long numero);
    // void deleteByNumero(@Param("numero") long numero);

    @Query("select p from LigneVente p where p.produit.id =:prod")
    List<LigneVente> ListLigneVenteByProduitId(@Param("prod") Long prodId);

    @Query("select p from LigneVente p where p.vente.id =:num")
    List<LigneVente> ListLigneVenteByVenteId(@Param("num") Long venteId);

    List<LigneVente> findByOrderByIdDesc();


}
