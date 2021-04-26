package com.library.repository;

import com.library.entities.LigneCmdClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LigneCmdClientRepository extends JpaRepository<LigneCmdClient, Long> {

    List<LigneCmdClient> findAllByNumero(Long numero);
    //  List<LigneCmdClient> findAllByNumero(long numero);

    @Modifying
    @Query("delete from LigneCmdClient where numero = :numero")
    void deleteByNumero(@Param("numero") Long numero);
    // void deleteByNumero(@Param("numero") long numero);

    @Query("select p from LigneCmdClient p where p.produit.id =:prod")
    List<LigneCmdClient> ListLigneCmdClientByProduitId(@Param("prod") Long prodId);

    @Query("select p from LigneCmdClient p where p.commande.id =:num")
    List<LigneCmdClient> ListLigneCmdClientByCommandeClientId(@Param("num") Long comId);


}
