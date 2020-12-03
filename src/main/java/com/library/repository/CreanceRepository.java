package com.library.repository;

import com.library.entities.Creance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;


@Repository
public interface CreanceRepository extends JpaRepository<Creance, Long> {


    @Query("select count(p) from Creance p ")
    Integer countNumberOfCreance();

    //	@Query("select count(*) from CommandeClient group by (dateCommande)")
    @Query("select sum(c.totalCreance) from Creance c")
    BigDecimal countNumbersOfCommandes();

    @Query("select p from Creance p where p.reference like :num")
    Creance findByNumeroCreance(@Param("num") int reference);


    @Query("select p from Creance p where p.status like :status")
    Creance findByStatus(@Param("status") String status);

    @Query("select c from Creance c where c.status like :status")
    List<Creance> ListCreanceByStatus(@Param("status") String status);


    @Query("select p from Creance p where p.client.id =:cl")
    List<Creance> ListCreanceClientByClientId(@Param("cl") Long clientId);

}
