package com.library.repository;

import com.library.entities.Vente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VenteRepository extends JpaRepository<Vente, Long> {

    @Query("select p from Vente p where p.numeroVente like :num")
    public Vente findByNumeroVente(@Param("num") String numeroVente);

    @Query("select c from Vente c where c.numeroVente like :num")
    public List<Vente> findListVenteByNumeroVente(@Param("num") String numCommande);

    @Query("select p from Vente p")
    public Page<Vente> findAllVenteByPageable(Pageable pageable);

    @Query("select p from Vente p where p.numeroVente like :mc")
    public Page<Vente> findVenteByKeyWord(@Param("mc") String mc, Pageable pageable);
}
