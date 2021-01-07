package com.library.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.library.entities.Fournisseur;

@Repository
public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {

	Fournisseur findByEmail(String email);
	
	@Query("select c from Fournisseur c where c.raisonSociale like :raison")
	Fournisseur findByRaisonSociale(@Param("raison") String raisonSociale);
	
	@Query("select c from Fournisseur c where c.raisonSociale like :r") 
	List<Fournisseur> ListFournisseurByRaisonSociale(@Param("r") String raisonSociale);
	

	@Query("select c from Fournisseur c where c.code like :code")
	Fournisseur findByCode(@Param("code") String code);
	
	@Query("select c from Fournisseur c where c.code like :c") 
	List<Fournisseur> ListFournisseurByCode(@Param("c") String code);
	
	
	
	@Query("select c from Fournisseur c where c.nom like :nom")
	public Fournisseur findByNom(@Param("nom") String nom);
	
	@Query("select c from Fournisseur c where c.nom like :nom") 
	public List<Fournisseur> ListFournisseurByNom(@Param("nom") String nom);
	
	@Query("select p from Fournisseur p")
	public Page<Fournisseur> findFournisseurByPageable(Pageable pageable);
	  
	@Query("select p from Fournisseur p where p.nom like :x")
	public Page<Fournisseur> findFournisseurByKeyWord(@Param("x") String mc, Pageable pageable);
	
	
}
