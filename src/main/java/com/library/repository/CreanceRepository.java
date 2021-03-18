package com.library.repository;

import com.library.entities.Creance;
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


    @Query("select count(p) from Creance p ")
    Integer countNumberOfCreance();

    @Modifying()
    @Query("update Creance c set c.status = :status where c.id = :id")
    void updateCreanceStatus(@Param("status") String status, @Param("id") Long id);

    @Modifying
    @Query("update Creance c set c.status = :status where c.id = :id")
    void setCreanceStatusById(@Param("status") String status, @Param("id") Long id);

    Optional<Creance> findByReference(long reference);

    Optional<Creance> findByCodeCreance(String CodeCreance);

    //	@Query("select count(*) from CommandeClient group by (dateCommande)")
    @Query("select sum(c.totalCreance) from Creance c")
    BigDecimal countNumbersOfCommandes();

    @Query("select p from Creance p where p.reference like :num")
    Creance findByNumeroCreance(@Param("num") long reference);


    @Query("select p from Creance p where p.status like :status")
    Creance findByStatus(@Param("status") String status);

    @Query("select c from Creance c where c.status like :status")
    List<Creance> ListCreanceByStatus(@Param("status") String status);


    @Query("select p from Creance p where p.client.id =:cl")
    List<Creance> ListCreanceClientByClientId(@Param("cl") Long clientId);

}
