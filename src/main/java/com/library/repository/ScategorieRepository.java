package com.library.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.library.entities.Scategorie;

@Repository
public interface ScategorieRepository extends JpaRepository<Scategorie, Long> {

	Scategorie findByCode(String code);
	
	@Query("select p from Scategorie p where p.code like :code") 
	public List<Scategorie> findListScategorieByCode(@Param("code") String code);
	
	
	@Query("select p from Scategorie p where p.libelle like :libelle")
	public Scategorie findByLibelle(@Param("libelle") String libelle);
	
	@Query("select p from Scategorie p where p.libelle like :libelle") 
	public List<Scategorie> findListScategorieByLibelle(@Param("libelle") String libelle);
	
	@Query("select p from Scategorie p where p.categorie.id =:cat")
	public List<Scategorie> findScategorieByCateoryId(@Param("cat") Long catId);
	
	@Query("select p from Scategorie p where p.categorie.id =:id")
	public Page<Scategorie> findScategorieByCateoryPageable(@Param("id") Long catId, Pageable pageable);
	
	@Query("select p from Scategorie p")
	public Page<Scategorie> findAllScategoriesByPageable(Pageable pageable);
	  
	@Query("select p from Scategorie p where p.libelle like :mc")
	public Page<Scategorie> findScategorieByKeyWord(@Param("mc") String mc, Pageable pageable);


}
