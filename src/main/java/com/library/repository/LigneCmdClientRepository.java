package com.library.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.library.entities.LigneCmdClient;

@Repository
public interface LigneCmdClientRepository extends JpaRepository<LigneCmdClient, Long> {
	
	
	@Query("select p from LigneCmdClient p where p.produit.id =:prod")
	public List<LigneCmdClient> ListLigneCmdClientByProduitId(@Param("prod") Long prodId);
	
	@Query("select p from LigneCmdClient p where p.commande.id =:num")
	public List<LigneCmdClient> ListLigneCmdClientByCommandeClientId(@Param("num") Long comId);
	
	@Query("select p from LigneCmdClient p where p.commande.id =:id")
	public Page<LigneCmdClient> findLigneCmdClientByCommandeClientPageable(@Param("id") Long comId, Pageable pageable);
	
	@Query("select p from LigneCmdClient p where p.produit.id =:id")
	public Page<LigneCmdClient> findLigneCmdClientByProduitPageable(@Param("id") Long prodId, Pageable pageable);
			
	@Query("select p from LigneCmdClient p")
	public Page<LigneCmdClient> findAllLigneCmdClientByPageable(Pageable pageable);
	  


}
