package com.library.repository;

import com.library.entities.CommandeClient;
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

    @Query("select sum(c.totalCommande) from CommandeClient c")
    BigDecimal countNumbersOfCommandes();

    @Query("select sum(c.totalCommande) from CommandeClient c where month(c.dateCommande) = month(current_date)")
    BigDecimal sumTotalOfCommandesByMonth();

    @Query("select sum(c.totalCommande) from CommandeClient c where year(c.dateCommande) = year(current_date)")
    BigDecimal sumTotalOfCommandesByYear();

    @Query("select p from CommandeClient p where p.numeroCommande like :num")
    CommandeClient findByNumeroCommande(@Param("num") Long numeroCommande);

    @Query("select p from CommandeClient p where p.status like :status")
    CommandeClient findByStatus(@Param("status") String status);

    @Query("select c from CommandeClient c where c.status like :status")
    List<CommandeClient> ListCommandeClientByStatus(@Param("status") String status);

    List<CommandeClient> findAllByDateCommande(Date dateCommande);


    //  @Query("select v from CommandeClient v where v.dateCommande < (current_date () - interval 3 month")
    @Query("select v from CommandeClient v where (v.dateCommande <= current_date - 90) ORDER BY v.dateCommande DESC ")
    List<CommandeClient> findListCommandeClientOf3LatestMonth();

    List<CommandeClient> findTop500ByOrderByIdDesc();

    @Query("select EXTRACT(month from(c.dateCommande)), count(c) from CommandeClient c group by EXTRACT(month from(c.dateCommande))")
    List<?> countNumberOfCommandeByMonth();

    @Query("select EXTRACT(month from(c.dateCommande)), sum(c.totalCommande) from CommandeClient c group by EXTRACT(month from(c.dateCommande))")
    List<?> sumTotalOfCommandeByMonth();

    @Query("select p from CommandeClient p where p.client.id =:cl")
    List<CommandeClient> ListCommandeClientByClientId(@Param("cl") Long clientId);

    List<CommandeClient> findByOrderByIdDesc();


}
