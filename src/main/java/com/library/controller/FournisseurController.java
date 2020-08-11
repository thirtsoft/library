package com.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
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

import com.library.entities.Fournisseur;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.FournisseurService;

@RestController
@CrossOrigin
@RequestMapping("/alAmine")
public class FournisseurController {
	
	@Autowired
	private FournisseurService fournisseurService;
	
	@GetMapping("/fournisseurs")
	public List<Fournisseur> getAllFournisseurs() {
		return fournisseurService.findAllFournisseurs();	
	}
	
	@GetMapping("/fournisseurs/{id}")
	public ResponseEntity<Fournisseur> getFournisseurById(@PathVariable(value = "id") Long id)
			throws ResourceNotFoundException{
		Fournisseur fournisseur = fournisseurService.findProduitById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Fournisseur that id is" + id + "not found"));
		return ResponseEntity.ok().body(fournisseur);
		
	}
	
	@GetMapping("/searchFournisseurByCode")
	public Fournisseur getFournisseurByCode(@RequestParam(value = "code") String code) {
		return fournisseurService.findByCode(code);
		
	}
	
	@GetMapping("/searchListFournisseurByCode")
	public List<Fournisseur> getAllFournisseurByCode(@RequestParam(value = "c") String code) {
		
		return fournisseurService.findListFournisseurByCode("%"+code+"%");
		
	}
	
	@GetMapping("/searchFournisseurByNom")
	public Fournisseur getFournisseurByNom(@RequestParam(value = "des") String nom) {
		return fournisseurService.findByNom(nom);
		
	}
	
	@GetMapping("/searchListFournisseurByDesignation")
	public List<Fournisseur> getAllFournisseurByNom(@RequestParam(value = "des") String nom) {
		
		return fournisseurService.findListFournisseurByNom("%"+nom+"%");
		
	}
	
	@GetMapping("/searchFournisseurByRaisonSocial")
	public Fournisseur getFournisseurByRaisonSocial(@RequestParam(value = "raison") String raisonSocial) {
		return fournisseurService.findByRaisonSociale(raisonSocial);
		
	}
	
	@GetMapping("/searchListFournisseurByRaisonSocial")
	public List<Fournisseur> getAllFournisseurByRaisonSocial(@RequestParam(value = "raison") String raisonSocial) {
		
		return fournisseurService.findListFournisseurByRaisonSociale("%"+raisonSocial+"%");
		
	}
	
	@GetMapping("/searchListFournisseurByPageable")
	public Page<Fournisseur> getAllFournisseurByPageable(@RequestParam(name = "page") int page,
			@RequestParam(name = "size") int size) {
		return fournisseurService.findAllFournisseursByPageable(PageRequest.of(page, size));
	}
	
	@GetMapping(value = "/searchListFournisseurByPageableParMotCle")
	public Page<Fournisseur> getFournisseurByKeyWord(@RequestParam(name="mc", defaultValue="") String mc, 
			@RequestParam(name = "page") int page,
			@RequestParam(name = "size") int size) {
		return fournisseurService.findFournisseurByKeyWord("%"+mc+"%", PageRequest.of(page, size));
	}
	
	
	@PostMapping("/fournisseurs") 
	public Fournisseur createFournisseur(@RequestBody Fournisseur fournisseur) {
		return fournisseurService.saveFournisseur(fournisseur);	
	}
	
	@PutMapping("/fournisseurs/{id}")
	public ResponseEntity<Fournisseur>  updateFournisseur(@PathVariable(value = "id") Long id, @RequestBody Fournisseur fournisseur) {
		fournisseur.setId(id);
		return new ResponseEntity<>(fournisseurService.saveFournisseur(fournisseur), HttpStatus.OK);
		
	}
	@DeleteMapping("/fournisseurs/{id}")
	public ResponseEntity<Object> deleteFournisseur(@PathVariable(value = "id") Long id) {
		return fournisseurService.deleteFournisseur(id);
		
	}

}
