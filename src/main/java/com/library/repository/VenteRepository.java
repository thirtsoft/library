package com.library.repository;

import com.library.entities.Vente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PatchMapping;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface VenteRepository extends JpaRepository<Vente, Long> {

    @Query("select count(p) from Vente p where p.dateVente between :d1 and :d2")
    Integer countBetween(@Param("d1")Date d1, @Param("d2")Date d2);

    @Query("select count(p) from Vente p ")
    Integer countNumberOfVente();

    @Query("select count(v) from Vente v where v.dateVente > current_date ")
    Integer countNumberOfVenteByDay();

    @Query("select sum(c.totalVente) from Vente c")
    BigDecimal sumTotalOfVentes();

    @Query("select v from Vente v where v.dateVente > current_date")
    List<Vente> findVenteWithParticularDayAndMonth();

    @Query("select sum(v.totalVente) from Vente v where v.dateVente > current_date")
    BigDecimal sumTotalOfVenteByDay();

    @Query("select EXTRACT(month from(v.dateVente)), sum(v.totalVente) from Vente v group by EXTRACT(month from(v.dateVente))")
    List<?> sumTotalOfVenteByMonth();

    @Query("select p from Vente p where p.numeroVente like :num")
    Vente findByNumeroVente(@Param("num") int numeroVente);
/*
    @Query("select c from Vente c where c.numeroVente like :num")
    public List<Vente> findListVenteByNumeroVente(@Param("num") String numCommande);
*/

    @Query("select p from CommandeClient p where p.status like :status")
    Vente findByStatus(@Param("status") String status);

    @Query("select c from Vente c where c.status like :status")
    public List<Vente> ListCommandeClientByStatus(@Param("status") String status);

    List<Vente> findAllByDateVente(Date dateVente);

    @Query("select p from Vente p")
    Page<Vente> findAllVenteByPageable(Pageable pageable);

    @Query("select p from Vente p where p.numeroVente like :mc")
    Page<Vente> findVenteByKeyWord(@Param("mc") String mc, Pageable pageable);
}
