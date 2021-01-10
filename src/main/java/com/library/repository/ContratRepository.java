package com.library.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.library.entities.Contrat;

@Repository
public interface ContratRepository extends JpaRepository<Contrat, Long> {

	Contrat findByReference(String reference);
	
	@Query("select p from Contrat p where p.reference like :ref") 
	List<Contrat> findListContratByReference(@Param("ref") String reference);
	
	
	@Query("select p from Contrat p where p.nature like :nat")
	Contrat findByNature(@Param("nat") String nature);
	
	@Query("select p from Contrat p where p.nature like :nat") 
	List<Contrat> findListContratByNature(@Param("nat") String nature);
	
	@Query("select p from Contrat p where p.client.id =:client")
	List<Contrat> findLitContratByClientId(@Param("client") Long clientId);
	
	@Query("select p from Contrat p where p.client.id =:id")
	Page<Contrat> findContratByClientPageable(@Param("id") Long clientId, Pageable pageable);
	
	@Query("select p from Contrat p")
	Page<Contrat> findAllContratsByPageable(Pageable pageable);
	  
	@Query("select p from Contrat p where p.nature like :mc")
	Page<Contrat> findContratsByKeyWord(@Param("mc") String mc, Pageable pageable);

	  	

}
