package com.library.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.entities.Scategorie;
import com.library.services.ScategorieService;

@RestController
@CrossOrigin
@RequestMapping("/alAmine")
public class ScategorieController {
	
	@Autowired
	private ScategorieService scategorieService;
	
	
	@GetMapping("/scategories")
	public List<Scategorie> getAllScategories() {
		return scategorieService.findAllScategories();
	}
	
	@GetMapping("/scategories/{id}")
	public Optional<Scategorie> getScategorieById(@PathVariable(value = "id") Long scatId) {
		return scategorieService.findScategorieById(scatId);
	}
	
	@GetMapping("/searchScategorieByCode")
	public Scategorie getScategorieByCode(@RequestParam(name = "code") String code) {
		return scategorieService.findByCode(code);
	}
	
	@GetMapping("/searchListScategoriesByCode")
	public List<Scategorie> getListScategoriesByCode(@RequestParam(name = "code") String code) {
		return scategorieService.findListScategorieByCode("%"+code+"%");
	}
	
	@GetMapping("/searchProduitByLibelle")
	public Scategorie getScategorieByLibelle(@RequestParam(name = "libelle") String libelle) {
		return scategorieService.findByLibelle(libelle);
	}
	
	@GetMapping("/searchListScategoriesByLibelle")
	public List<Scategorie> getListScategoriesByLibelle(@RequestParam(name = "libelle") String libelle) {
		return scategorieService.findListScategorieByLibelle("%"+libelle+"%");
	}
	
	@GetMapping("/searchListScategoriesByCategoryId/{catId}")
	public List<Scategorie> getListScategoriesByCategoryId(@PathVariable (name="catId") Long catId) {
<<<<<<< HEAD
=======
		return scategorieService.findScategorieByCateoryId(catId);
	}

	@GetMapping("/searchListScategoriesByCategoryId")
	public List<Scategorie> getListScategoriesByCategory(@RequestParam (name="catId") Long catId) {
>>>>>>> 962d992518874a2014c813f38e02d77021502842
		return scategorieService.findScategorieByCateoryId(catId);
	}
	
	@GetMapping("/searchListScategoriesByCategoryPageable")
	public Page<Scategorie> getAllScategoriesByPageable(@RequestParam(name = "cat")Long catId,
			@RequestParam(name = "page") int page,
			@RequestParam(name = "size") int size) {
		return scategorieService.findAllScategoriesByCategory(catId, PageRequest.of(page, size));
	}
	
	@GetMapping("/searchListScategoriesByPageable")
	public Page<Scategorie> getAllScategoriesByPageable(@RequestParam(name = "page") int page,
			@RequestParam(name = "size") int size) {
		return scategorieService.findAllScategorietsByPageable(PageRequest.of(page, size));
	}
	
	@GetMapping("/searchListScategoriesByKeyword")
	public Page<Scategorie> getAllScategoriesByKeyword(@RequestParam(name = "mc") String mc, 
			@RequestParam(name = "page") int page,
			@RequestParam(name = "size") int size) {
		return scategorieService.findScategorieByKeyWord("%"+mc+"%", PageRequest.of(page, size));
		
	}
	
	
	@PostMapping("/scategories")
	public Scategorie saveScategorie(@RequestBody Scategorie sCategorie) {
		return scategorieService.saveScategorie(sCategorie);
	}
	
	@PutMapping("/scategories/{ScatId}")
	public Scategorie updateScategorie(@PathVariable Long ScatId, @RequestBody Scategorie sCategorie) {
		sCategorie.setId(ScatId);
		return scategorieService.saveScategorie(sCategorie);	
	}
	
	@DeleteMapping("/scategories/{id}")
	public ResponseEntity<Object> deleteScategorie(@PathVariable(value="id") Long ScatId) {
		return scategorieService.deleteScategorie(ScatId);
	}
	

}
