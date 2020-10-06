package com.library.repository;

import com.library.entities.Creance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.library.entities.Contrat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


@Repository
public interface CreanceRepository extends JpaRepository<Creance, Long> {

    @Query("select p from Creance p where p.reference like :ref")
    public Creance findByReference(@Param("ref") String reference);

    @Query("select p from Creance p where p.reference like :ref")
    public List<Creance> findListContratByReference(@Param("ref") String reference);


    @Query("select p from Creance p where p.libelle like :lib")
    public Creance findByLibelle(@Param("lib") String libelle);

    @Query("select p from Creance p where p.libelle like :lib")
    public List<Creance> findListContratByLibelle(@Param("lib") String libelle);

    @Query("select p from Creance p where p.client.id =:client")
    public List<Creance> findLitCreanceByClientId(@Param("client") Long clientId);

    @Query("select p from Creance p where p.client.id =:id")
    public Page<Creance> findCreanceByClientPageable(@Param("id") Long clientId, Pageable pageable);

    @Query("select p from Creance p")
    public Page<Creance> findAllCreancesByPageable(Pageable pageable);

    @Query("select p from Creance p where p.libelle like :mc")
    public Page<Creance> findCreancesByKeyWord(@Param("mc") String mc, Pageable pageable);

}
