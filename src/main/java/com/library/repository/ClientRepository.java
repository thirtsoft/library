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
	
	@Query("select c from Client c where c.raisonSocial like :raisonSocial")
	public Client findByRaisonSocial(@Param("raisonSocial") String raisonSocial);
	
	@Query("select c from Client c where c.raisonSocial like :raisonSocial") 
	public List<Client> ListClientByRaisonSocial(@Param("raisonSocial") String raisonSocial);
	
	@Query("select c from Client c where c.chefService like :chefService")
	public Client findByChefService(@Param("chefService") String chefService);
	
	@Query("select c from Client c where c.chefService like :chefService") 
	public List<Client> ListClientByChefService(@Param("chefService") String chefService);

	@Query("select c.raisonSocial, count(c) as countClient from Client c group by c.raisonSocial")
	List<Object[]> ListClientGroupByRaisonSocial();
//	@Query("SELECT u.serverName,count(u) as controlRunCount from RunList u where u.controlRunDate < :lastUploadDate group by u.serverName")
//	List<Object[]> findAllControlRunAfterDate(@Param("lastUploadDate") Date lastUploadDate);
	//@Query("SELECT c.year, COUNT(c.year) FROM Comment AS c GROUP BY c.year ORDER BY c.year DESC")
	//List<Object[]> countTotalCommentsByYear();



	@Query("select p from Client p")
	public Page<Client> findClientByPageable(Pageable pageable);
	  
	@Query("select p from Client p where p.raisonSocial like :x")
	public Page<Client> findClientByKeyWord(@Param("x") String mc, Pageable pageable);
	
	

}
