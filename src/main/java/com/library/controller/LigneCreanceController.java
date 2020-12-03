package com.library.controller;

import com.library.entities.LigneCmdClient;
import com.library.entities.LigneCreance;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.LigneCmdClientService;
import com.library.services.LigneCreanceService;
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
public class LigneCreanceController {
	
	@Autowired
	private LigneCreanceService ligneCreanceService;
	
	@GetMapping("/ligneCreances")
	public List<LigneCreance> getAllLigneCreances() {
		return ligneCreanceService.findAllLigneCreances();
		
	}
	
	@GetMapping("/ligneCreances/{id}")
	public ResponseEntity<LigneCreance> getLigneCreanceById(@PathVariable(value = "id") Long lCreanceId)
			throws ResourceNotFoundException{
		LigneCreance ligneCreance = ligneCreanceService.findLigneCreanceById(lCreanceId)
				.orElseThrow(()-> new ResourceNotFoundException("LigneCreance that id is" + lCreanceId + "not found"));
		return ResponseEntity.ok().body(ligneCreance);
		
	}

	@GetMapping("/lcreances/{id}")
	public List<LigneCreance> getAllByNumero(@PathVariable(value = "id") int numero) {
		System.out.println("Get all Lcreances...");

		List<LigneCreance> Lcreances = new ArrayList<>();
		ligneCreanceService.findAllLcreanceByNumero(numero).forEach(Lcreances::add);


		return Lcreances;
	}
	
	@GetMapping("/searchListLigneCreanceByProduitId")
	public List<LigneCreance> getAllLigneCreanceByProduitId(@RequestParam("prodId") Long prodId) {
		return ligneCreanceService.findLigneCreanceByProduitId(prodId);
	}

	@GetMapping("/searchListLigneCreanceByCreanceId/{creanceId}")
	public List<LigneCreance> getAllLigneCreanceByCreanceId(@PathVariable("creanceId") Long creanceId) {
		return ligneCreanceService.findLigneCreanceByCreanceId(creanceId);
	}

	@PostMapping("/ligneCreances")
	public LigneCreance createLigneCreance(@RequestBody LigneCreance ligneCreance) {
		return ligneCreanceService.saveLigneCreance(ligneCreance);
	}
	
	@PutMapping("/ligneCreance/{lcId}")
	public ResponseEntity<LigneCreance>  updateLigneCreance(@PathVariable(value = "lcId") Long lcId, @RequestBody LigneCreance ligneCreance) {
		ligneCreance.setId(lcId);
		return new ResponseEntity<>(ligneCreanceService.saveLigneCreance(ligneCreance), HttpStatus.OK);
		
	}
	@DeleteMapping("/ligneCreance/{id}")
	public ResponseEntity<Object> deleteLigneCreance(@PathVariable(value = "id") Long id) {
		return ligneCreanceService.deleteLigneCreance(id);
		
	}

}
