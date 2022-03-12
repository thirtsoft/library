package com.library.repository;

import com.library.entities.Creance;
import com.library.entities.Vente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Repository
public interface CreanceRepository extends JpaRepository<Creance, Long> {

    Optional<Creance> findByReference(long reference);

    Optional<Creance> findByCodeCreance(String CodeCreance);

    @Query("select p from Creance p where p.reference like :num")
    Creance findByNumeroCreance(@Param("num") Long reference);

    @Query("select p from Creance p where p.status like :status")
    Creance findByStatus(@Param("status") String status);

    @Modifying()
    @Query("update Creance c set c.status = :status where c.id = :id")
    void updateCreanceStatus(@Param("status") String status, @Param("id") Long id);

    @Modifying
    @Query("update Creance c set c.status = :status where c.id = :id")
    void setCreanceStatusById(@Param("status") String status, @Param("id") Long id);

    @Query("select count(p) from Creance p ")
    Integer countNumberOfCreance();

    @Query("select sum(c.totalCreance) from Creance c")
    BigDecimal countNumbersOfCommandes();

    @Query("select sum((c.totalCreance)-(c.avanceCreance)) from Creance c where year(c.dateCreance) = year(current_date)")
    BigDecimal sumTotalOfCreanceByYear();

    @Query("select EXTRACT(month from(c.dateCreance)), sum(c.totalCreance) from Creance c group by EXTRACT(month from(c.dateCreance))")
    List<?> sumTotalOfCreancesByMonth();

    @Query("select c from Creance c where c.status like :status")
    List<Creance> ListCreanceByStatus(@Param("status") String status);

    @Query("select p from Creance p where p.client.id =:cl")
    List<Creance> ListCreanceClientByClientId(@Param("cl") Long clientId);

    @Query("select p from Creance p where p.client.id =:cl and p.status!='PAYEE' order by id Desc")
    List<Creance> ListCreanceClientByClientIdAndStatus(@Param("cl") Long clientId);

    List<Creance> findByOrderByIdDesc();

    //  @Query("select v from Creance v where v.dateCreance < (current_date () - interval 3 month")
    @Query("select v from Creance v where (v.dateCreance <= current_date - 90) ORDER BY v.dateCreance DESC ")
    List<Creance> findListCreanceOf3LatestMonth();

    List<Creance> findTop500ByOrderByIdDesc();

}
