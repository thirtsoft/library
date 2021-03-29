package com.library.repository;

import com.library.entities.Devis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface DevisRepository extends JpaRepository<Devis, Long> {

    @Query("select count(p) from Devis p where p.dateDevis between :d1 and :d2")
    Integer countBetween(@Param("d1") Date d1, @Param("d2") Date d2);

    @Query("select count(p) from Devis p ")
    Integer countNumberOfDevis();

    //	@Query("select count(*) from Devis group by (dateCommande)")
    @Query("select sum(c.totalDevis) from Devis c")
    BigDecimal countNumbersOfDevis();

    @Query("select p from Devis p where p.numeroDevis like :num")
    Devis findByNumeroDevis(@Param("num") Long numeroDevis);
   // Devis findByNumeroDevis(@Param("num") long numeroDevis);

    List<Devis> findAllByDateDevis(Date dateDevis);

    @Query("select EXTRACT(month from(c.dateDevis)), count(c) from Devis c group by EXTRACT(month from(c.dateDevis))")
    List<?> countNumberOfDevisByMonth();

    @Query("select EXTRACT(month from(c.dateDevis)), sum(c.totalDevis) from Devis c group by EXTRACT(month from(c.dateDevis))")
    List<?> sumTotalOfDevisByMonth();

    @Query("select p from Devis p where p.client.id =:cl")
    List<Devis> ListDevisByClientId(@Param("cl") Long clientId);

}
