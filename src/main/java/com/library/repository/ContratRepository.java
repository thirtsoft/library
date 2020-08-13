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
	
	@Query("select p from Contrat p where p.reference like :ref")
	public Contrat findByReference(@Param("ref") String reference);
	
	@Query("select p from Contrat p where p.reference like :ref") 
	public List<Contrat> findListContratByReference(@Param("ref") String reference);
	
	
	@Query("select p from Contrat p where p.nature like :nat")
	public Contrat findByNature(@Param("nat") String nature);
	
	@Query("select p from Contrat p where p.nature like :nat") 
	public List<Contrat> findListContratByNature(@Param("nat") String nature);
	
	@Query("select p from Contrat p where p.client.id =:client")
	public List<Contrat> findLitContratByClientId(@Param("client") Long clientId);
	
	@Query("select p from Contrat p where p.client.id =:id")
	public Page<Contrat> findContratByClientPageable(@Param("id") Long clientId, Pageable pageable);
	
	@Query("select p from Contrat p")
	public Page<Contrat> findAllContratsByPageable(Pageable pageable);
	  
	@Query("select p from Contrat p where p.nature like :mc")
	public Page<Contrat> findContratsByKeyWord(@Param("mc") String mc, Pageable pageable);

	  	

}
