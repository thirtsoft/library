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

import com.library.entities.LigneCmdClient;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.LigneCmdClientService;

@RestController
@CrossOrigin
@RequestMapping("/alAmine")
public class LigneCmdClientController {
	
	@Autowired
	private LigneCmdClientService ligneCmdClientService;
	
	@GetMapping("/ligneCommandes")
	public List<LigneCmdClient> getAllLigneCmdClients() {
		return ligneCmdClientService.findAllLigneCmdClient();
		
	}
	
	@GetMapping("/ligneCommandes/{id}")
	public ResponseEntity<LigneCmdClient> getLigneCmdClientById(@PathVariable(value = "id") Long id)
			throws ResourceNotFoundException{
		LigneCmdClient ligneCmdClient = ligneCmdClientService.findLigneCmdClientById(id)
				.orElseThrow(()-> new ResourceNotFoundException("LigneCmdClient that id is" + id + "not found"));
		return ResponseEntity.ok().body(ligneCmdClient);
		
	}
	
	@GetMapping("/searchListLigneCmdClientByProduitId")
	public List<LigneCmdClient> getAllLigneCmdClientByProduitId(@RequestParam("prodId") Long prodId) {
		return ligneCmdClientService.findLigneCmdClientByProduitId(prodId);
	}
	
	@GetMapping("/searchListLigneCmdClientByProduitPageable")
	public Page<LigneCmdClient> getAllProduitsByPageable(@RequestParam(name = "prod")Long prodId,
			@RequestParam(name = "page") int page,
			@RequestParam(name = "size") int size) {
		return ligneCmdClientService.findAllLigneCmdClientByProduit(prodId, PageRequest.of(page, size));
	}
	
	@GetMapping("/searchListLigneCmdClientByCommandeId")
	public List<LigneCmdClient> getAllLigneCmdClientByCommandeId(@RequestParam("comId") Long comId) {
		return ligneCmdClientService.findLigneCmdClientByCommandeClientId(comId);
	}
	
	@GetMapping("/searchListLigneCmdClientByCommandePageable")
	public Page<LigneCmdClient> getAllCommandeByPageable(@RequestParam(name = "prod")Long comId,
			@RequestParam(name = "page") int page,
			@RequestParam(name = "size") int size) {
		return ligneCmdClientService.findAllLigneCmdClientByCommandeClient(comId, PageRequest.of(page, size));
	}
	
	@PostMapping("/ligneCommandes") 
	public LigneCmdClient createLigneCmdClient(@RequestBody LigneCmdClient ligneCmdClient) {
		return ligneCmdClientService.saveLigneCmdClient(ligneCmdClient);	
	}
	
	@PutMapping("/ligneCommandes/{lcId}")
	public ResponseEntity<LigneCmdClient>  updateLigneCmdClient(@PathVariable(value = "lcId") Long lcId, @RequestBody LigneCmdClient ligneCmdClient) {
		ligneCmdClient.setId(lcId);
		return new ResponseEntity<>(ligneCmdClientService.saveLigneCmdClient(ligneCmdClient), HttpStatus.OK);
		
	}
	@DeleteMapping("/ligneCommandes/{id}")
	public ResponseEntity<Object> deleteLigneCmdClient(@PathVariable(value = "id") Long id) {
		return ligneCmdClientService.deleteLigneCmdClient(id);
		
	}

}
