package com.library.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.library.entities.Produit;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
	
	@Query("select p from Produit p where p.designation like :des")
	public Produit findByDesignation(@Param("des") String designation);
	
	@Query("select p from Produit p where p.categorie.id =:id")
	Page<Produit> getProduitByCateoryId(@Param("id") Long catId, Pageable pageable);
	
	@Query("select p from Produit p where p.categorie.id =:cat")
	public List<Produit> getProductByCateoryId(@Param("cat") Long catId);
		
	@Query("select p from Produit p where p.designation like :des") 
	public List<Produit> searchProductByDesignation(@Param("des") String designation);
	
	@Query("select p from Produit p")
	public Page<Produit> findAllProduitsByPageable(Pageable pageable);
	  
	@Query("select p from Produit p where p.designation like :mc")
	public Page<Produit> findProduitByKeyWord(@Param("mc") String mc, Pageable pageable);
	  
	
	
}
