package com.library.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import com.library.entities.Scategorie;

public interface ScategorieService {
	
	public List<Scategorie> findAllScategories();
	public Optional<Scategorie> findScategorieById(Long sCatId);
	
	public Scategorie findByCode(String code);
	public List<Scategorie> findListScategorieByCode(String code);
	
	public Scategorie findByLibelle(String libelle);
	public List<Scategorie> findListScategorieByLibelle(String libelle);
	
	public List<Scategorie> findScategorieByCateoryId(Long catId);
	
	public Scategorie saveScategorie(Scategorie sCategorie);
	public Scategorie updateScategorie(Long sCatId, Scategorie sCategorie);
	public ResponseEntity<Object> deleteScategorie(Long sCatId);
	
	public Page<Scategorie> findAllScategorietsByPageable(Pageable page);
	public Page<Scategorie>findAllScategoriesByCategory(Long catId, Pageable pageable);
	
	public Page<Scategorie> findScategorieByKeyWord(String mc, Pageable pageable);
	
	
}
