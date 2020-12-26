package com.library.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import com.library.entities.Produit;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
	
	@Query("select p from Produit p where p.reference like :ref")
	public Produit findByReference(@Param("ref") String reference);
	
	@Query("select p from Produit p where p.designation like :des")
	public Produit findByDesignation(@Param("des") String designation);
	
	@Query("select p from Produit p where p.prixAchat like :price")
	public Produit findByPrixAchat(@Param("price") Double prixAchat);

	@Query("select p from Produit p where p.scategorie.id =:scat")
	public List<Produit> findProductByScateoryId(@Param("scat") Long scatId);

	@Query("select (p.scategorie.libelle), count(p) from Produit p where (p.qtestock > p.stockInitial) group by(p.scategorie)")
	List<?> countNumberOfProduitWithStoc();

	@Query("select p from Produit p where p.designation like :des") 
	public List<Produit> findListProduitByDesignation(@Param("des") String designation);

	@Query("select p from Produit p where p.add_date like :date")
	public List<Produit> findByAdd_date(@DateTimeFormat(pattern="yyyy-MM-dd") @Param("date")Date add_date);
	
	@Query("select p from Produit p")
	public Page<Produit> findAllProduitsByPageable(Pageable pageable);
	  
	@Query("select p from Produit p where p.designation like :mc")
	public Page<Produit> findProduitByKeyWord(@Param("mc") String mc, Pageable pageable);

	  	
}
