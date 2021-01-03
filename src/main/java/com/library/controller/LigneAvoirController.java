package com.library.controller;

import com.library.entities.LigneAvoir;
import com.library.entities.LigneCmdClient;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.LigneAvoirService;
import com.library.services.LigneCmdClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/alAmine")
public class LigneAvoirController {
	
	@Autowired
	private LigneAvoirService ligneAvoirService;

	@GetMapping("/ligneAvoirs")
	public List<LigneAvoir> getAllLigneAvoirs() {
		return ligneAvoirService.findAllLigneAvoirs();

	}

	@GetMapping("/ligneAvoirs/{id}")
	public ResponseEntity<LigneAvoir> getLigneAvoirById(@PathVariable(value = "id") Long id)
			throws ResourceNotFoundException{
		LigneAvoir ligneAvoir = ligneAvoirService.findLigneAvoirById(id)
				.orElseThrow(()-> new ResourceNotFoundException("LigneCmdClient that id is" + id + "not found"));
		return ResponseEntity.ok().body(ligneAvoir);

	}

	@GetMapping("/lavoirs/{id}")
	public List<LigneAvoir> getAllByNumero(@PathVariable(value = "id") int numero) {
		System.out.println("Get all Lavoirs...");

		List<LigneAvoir> Lavoirs = new ArrayList<>();
		ligneAvoirService.findAllLavoirByNumero(numero).forEach(Lavoirs::add);

		return Lavoirs;
	}

	@GetMapping("/searchListLigneAvoirByProduitId")
	public List<LigneAvoir> getAllLigneAvoirByProduitId(@RequestParam("prodId") Long prodId) {
		return ligneAvoirService.findLigneAvoirByProduitId(prodId);
	}

	@GetMapping("/searchListLigneAvoirByAvoirId/{avoirId}")
	public List<LigneAvoir> getAllLigneAvoirByAvoirId(@PathVariable("avoirId") Long avoirId) {
		return ligneAvoirService.findLigneAvoirByAvoirId(avoirId);
	}

	@PostMapping("/ligneAvoirs")
	public LigneAvoir createLigneAvoir(@RequestBody LigneAvoir ligneAvoir) {
		return ligneAvoirService.saveLigneAvoir(ligneAvoir);
	}

	@PutMapping("/ligneAvoirs/{lcId}")
	public ResponseEntity<LigneAvoir>  updateLigneAvoir(@PathVariable(value = "lcId") Long lcId, @RequestBody LigneAvoir ligneAvoir) {
		ligneAvoir.setId(lcId);
		return new ResponseEntity<>(ligneAvoirService.saveLigneAvoir(ligneAvoir), HttpStatus.OK);

	}
	@DeleteMapping("/ligneAvoirs/{id}")
	public void deleteLigneAvoir(@PathVariable(value = "id") int numero) {
		ligneAvoirService.deleteLavoirByNumero(numero);

	}
	

}