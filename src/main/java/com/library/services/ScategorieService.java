package com.library.services;

import java.util.List;
import java.util.Optional;

import com.library.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import com.library.entities.Scategorie;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ScategorieService {
	
	List<Scategorie> findAllScategories();
	Optional<Scategorie> findScategorieById(Long sCatId);
	
	Scategorie findByCode(String code);
	List<Scategorie> findListScategorieByCode(String code);
	
	Scategorie findByLibelle(String libelle);
	List<Scategorie> findListScategorieByLibelle(String libelle);
	
	List<Scategorie> findScategorieByCateoryId(Long catId);
	
	Scategorie saveScategorie(Scategorie sCategorie);
	Scategorie updateScategorie(Long sCatId, Scategorie sCategorie);
	void deleteScategorie(Long sCatId);
	
	Page<Scategorie> findAllScategorietsByPageable(Pageable page);
	Page<Scategorie>findAllScategoriesByCategory(Long catId, Pageable pageable);
	
	Page<Scategorie> findScategorieByKeyWord(String mc, Pageable pageable);

	boolean createScategoriePdf(List<Scategorie> scategories, ServletContext context, HttpServletRequest request, HttpServletResponse response);

	boolean createScategorieExcel(List<Scategorie> scategories, ServletContext context, HttpServletRequest request, HttpServletResponse response);


}
