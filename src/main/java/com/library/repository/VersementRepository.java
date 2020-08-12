package com.library.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.library.entities.Versement;

@Repository
public interface VersementRepository extends JpaRepository<Versement, Long> {
	
	@Query("select p from Versement p where p.numVersement like :num")
	public Versement findByNumVersement(@Param("num") String numVersement);
	
	@Query("select p from Versement p where p.numVersement like :num") 
	public List<Versement> findListVersementByNumVersement(@Param("num") String numVersement);
	
	
	@Query("select p from Versement p where p.nature like :nat")
	public Versement findByNature(@Param("nat") String nature);
	
	@Query("select p from Versement p where p.nature like :nat") 
	public List<Versement> findListVersementNature(@Param("nat") String nature);
	
	@Query("select p from Versement p where p.employe.id =:emp")
	public List<Versement> findVersementByEmployeId(@Param("emp") Long empId);
	
	@Query("select p from Versement p where p.employe.id =:id")
	public Page<Versement> findVersementByEmployeId(@Param("id") Long empId, Pageable pageable);
	
	@Query("select p from Versement p")
	public Page<Versement> findAllVersementsByPageable(Pageable pageable);
	  
	@Query("select p from Versement p where p.nature like :mc")
	public Page<Versement> findVersementByKeyWord(@Param("mc") String mc, Pageable pageable);


}
