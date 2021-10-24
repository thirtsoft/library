package com.library.repository;

import com.library.entities.Utilisateur;
import com.library.entities.Vente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface VenteRepository extends JpaRepository<Vente, Long> {

    @Query("select count(p) from Vente p where p.dateVente between :d1 and :d2")
    Integer countBetween(@Param("d1") Date d1, @Param("d2") Date d2);

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

    @Query("select sum(v.totalVente) from Vente v where month(v.dateVente) = month(current_date)")
    BigDecimal sumTotalOfVentesByMonth();

    @Query("select sum(v.totalVente) from Vente v where year(v.dateVente) = year(current_date)")
    BigDecimal sumTotalOfVentesByYear();

    @Query("select EXTRACT(month from(v.dateVente)), count(v) from Vente v group by EXTRACT(month from(v.dateVente))")
    List<?> countNumberOfVenteByMonth();

    @Query("select EXTRACT(month from(v.dateVente)), sum(v.totalVente) from Vente v group by EXTRACT(month from(v.dateVente))")
    List<?> sumTotalOfVenteByMonth();

    @Query("select EXTRACT(year from(v.dateVente)), sum(v.totalVente) from Vente v group by EXTRACT(year from(v.dateVente))")
    List<?> sumTotalOfVenteByYears();

    @Query("select p from Vente p where p.numeroVente like :num")
    Vente findByNumeroVente(@Param("num") Long numeroVente);
    // Vente findByNumeroVente(@Param("num") long numeroVente);

    @Query("select p from CommandeClient p where p.status like :status")
    Vente findByStatus(@Param("status") String status);

    @Query("select c from Vente c where c.status like :status")
    List<Vente> ListCommandeClientByStatus(@Param("status") String status);

    @Query("select v from Vente v where v.utilisateur.id =:emp")
    List<Vente> findAllVenteByEmployeId(@Param("emp") Long empId);

    List<Vente> findAllByDateVente(Date dateVente);

    List<Vente> findByOrderByIdDesc();

}
