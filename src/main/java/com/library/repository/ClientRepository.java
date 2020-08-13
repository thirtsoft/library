package com.library.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.library.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
	
	@Query("select c from Client c where c.nom like :name")
	public Client findByNom(@Param("name") String nom);
	
	@Query("select c from Client c where c.nom like :name") 
	public List<Client> ListClientByNom(@Param("name") String nom);
	
	@Query("select c from Client c where c.prenom like :pren")
	public Client findByPrenom(@Param("pren") String prenom);
	
	@Query("select c from Client c where c.prenom like :pren") 
	public List<Client> ListClientByPrenom(@Param("pren") String prenom);
	
	@Query("select p from Client p")
	public Page<Client> findClientByPageable(Pageable pageable);
	  
	@Query("select p from Client p where p.nom like :x")
	public Page<Client> findClientByKeyWord(@Param("x") String mc, Pageable pageable);
	
	

}
