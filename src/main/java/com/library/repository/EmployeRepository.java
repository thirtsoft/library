package com.library.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.library.entities.Employe;

@Repository
public interface EmployeRepository extends JpaRepository<Employe, Long> {
	
	@Query("select c from Employe c where c.cni like :cni")
	public Employe findByCni(@Param("cni") String cni);
	
	@Query("select c from Employe c where c.cni like :cni") 
	public List<Employe> ListEmployeByCni(@Param("cni") String cni);
	
	@Query("select c from Employe c where c.email like :mail")
	public Employe findByEmail(@Param("mail") String email);
	
	@Query("select c from Employe c where c.email like :mail") 
	public List<Employe> ListEmployeByEmail(@Param("mail") String email);
	
	@Query("select c from Employe c where c.nom like :name")
	public Employe findByNom(@Param("name") String nom);
	
	@Query("select c from Employe c where c.nom like :name") 
	public List<Employe> ListEmployeByNom(@Param("name") String nom);
	
	@Query("select c from Employe c where c.prenom like :pren")
	public Employe findByPrenom(@Param("pren") String prenom);
	
	@Query("select c from Employe c where c.prenom like :pren") 
	public List<Employe> ListEmployeByPrenom(@Param("pren") String prenom);
	
	@Query("select c from Employe c where c.telephone like :tel")
	public Employe findByTelephone(@Param("tel") String telephone);
	
	@Query("select c from Employe c where c.telephone like :tel") 
	public List<Employe> ListEmployeByTelephone(@Param("tel") String telephone);
	
	
	@Query("select p from Employe p")
	public Page<Employe> findEmployeByPageable(Pageable pageable);
	  
	@Query("select p from Employe p where p.nom like :x")
	public Page<Employe> findEmployeByKeyWord(@Param("x") String mc, Pageable pageable);
	
}
