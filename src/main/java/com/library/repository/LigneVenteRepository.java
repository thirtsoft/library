package com.library.repository;

import com.library.entities.LigneVente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LigneVenteRepository extends JpaRepository<LigneVente, Long> {

    @Query("select p from LigneVente p where p.produit.id =:prod")
    public List<LigneVente> ListLigneVenteByProduitId(@Param("prod") Long prodId);

    @Query("select p from LigneVente p where p.vente.id =:num")
    public List<LigneVente> ListLigneVenteByVenteId(@Param("num") Long venteId);

    @Query("select p from LigneVente p where p.vente.id =:id")
    public Page<LigneVente> findLigneVenteByVentePageable(@Param("id") Long venteId, Pageable pageable);

    @Query("select p from LigneVente p where p.produit.id =:id")
    public Page<LigneVente> findLigneVenteByProduitPageable(@Param("id") Long prodId, Pageable pageable);

    @Query("select p from LigneVente p")
    public Page<LigneVente> findAllLigneVenteByPageable(Pageable pageable);

}
