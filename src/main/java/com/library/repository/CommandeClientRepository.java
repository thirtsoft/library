package com.library.repository;

import com.library.entities.CommandeClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface CommandeClientRepository extends JpaRepository<CommandeClient, Long> {

    @Query("select count(p) from CommandeClient p where p.dateCommande between :d1 and :d2")
    Integer countBetween(@Param("d1") Date d1, @Param("d2") Date d2);

    @Query("select count(p) from CommandeClient p ")
    Integer countNumberOfCommande();

    //	@Query("select count(*) from CommandeClient group by (dateCommande)")
    @Query("select sum(c.totalCommande) from CommandeClient c")
    BigDecimal countNumbersOfCommandes();

    @Query("select p from CommandeClient p where p.numeroCommande like :num")
    CommandeClient findByNumeroCommande(@Param("num") long numeroCommande);

    @Query("select p from CommandeClient p where p.status like :status")
    CommandeClient findByStatus(@Param("status") String status);

    @Query("select c from CommandeClient c where c.status like :status")
    List<CommandeClient> ListCommandeClientByStatus(@Param("status") String status);

    List<CommandeClient> findAllByDateCommande(Date dateCommande);

    @Query("select EXTRACT(month from(c.dateCommande)), count(c) from CommandeClient c group by EXTRACT(month from(c.dateCommande))")
    List<?> countNumberOfCommandeByMonth();

    @Query("select EXTRACT(month from(c.dateCommande)), sum(c.totalCommande) from CommandeClient c group by EXTRACT(month from(c.dateCommande))")
    List<?> sumTotalOfCommandeByMonth();

    @Query("select p from CommandeClient p where p.client.id =:id")
    Page<CommandeClient> findCommandeClientByClientId(@Param("id") Long clientId, Pageable pageable);

    @Query("select p from CommandeClient p where p.client.id =:cl")
    List<CommandeClient> ListCommandeClientByClientId(@Param("cl") Long clientId);

    @Query("select p from CommandeClient p")
    Page<CommandeClient> findAllCommandeClientByPageable(Pageable pageable);

}
