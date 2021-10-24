package com.library.repository;

import com.library.entities.CommandeClient;
import com.library.entities.Contrat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContratRepository extends JpaRepository<Contrat, Long> {

    Contrat findByReference(String reference);

    @Query("select p from Contrat p where p.nature like :nat")
    Contrat findByNature(@Param("nat") String nature);

    @Query("select p from Contrat p where p.reference like :ref")
    List<Contrat> findListContratByReference(@Param("ref") String reference);

    @Query("select p from Contrat p where p.nature like :nat")
    List<Contrat> findListContratByNature(@Param("nat") String nature);

    @Query("select p from Contrat p where p.client.id =:client")
    List<Contrat> findLitContratByClientId(@Param("client") Long clientId);

    List<Contrat> findByOrderByIdDesc();


}
