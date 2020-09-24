package com.library.controller;

import java.util.Date;
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

import com.library.entities.CommandeClient;
import com.library.entities.LigneCmdClient;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.CommandeClientRepository;
import com.library.repository.LigneCmdClientRepository;
import com.library.services.ClientService;
import com.library.services.CommandeClientService;
import com.library.services.LigneCmdClientService;

@RestController
@CrossOrigin
@RequestMapping("/alAmine")
public class CommandeClientController {
	
	@Autowired
	private CommandeClientService commandeClientService;
	
	@Autowired
	private LigneCmdClientService ligneCmdClientService;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private CommandeClientRepository commandeClientRepository;
	
	@Autowired
	private LigneCmdClientRepository ligneCmdClientRepository;
	
	private Double total = 0.0;
	
	@GetMapping("/commandes")
	public List<CommandeClient> getAllCommandeClients() {
		return commandeClientService.findAllCommandeClient();
		
	}
	
	@GetMapping("/commandes/{id}")
	public ResponseEntity<CommandeClient> getCommandeClientById(@PathVariable(value = "id") Long id)
			throws ResourceNotFoundException{
		CommandeClient commandeClient = commandeClientService.findCommandeClientById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Commande that id is" + id + "not found"));
		return ResponseEntity.ok().body(commandeClient);
		
	}
	
	@GetMapping("/searchCommandeClientByNumCommande")
	public CommandeClient getCommandeClientByNumCommande(@RequestParam("num") String numCommande) {
		return commandeClientService.findByNumCommande(numCommande);
	}
	
	@GetMapping("/searchListCommandeClientByNumCommande")
	public List<CommandeClient> getAllCommandeClientByNumCommande(@RequestParam("num") String numCommande) {
		return commandeClientService.findListCommandeClientByNumCommande(numCommande);
	}
	
	@GetMapping("/searchCommandeClientByStatus")
	public CommandeClient getCommandeClientByStatus(@RequestParam("status") String status) {
		return commandeClientService.findByStatus(status);
	}
	
	@GetMapping("/searchListCommandeClientByStatus")
	public List<CommandeClient> getAllCommandeClientByStatus(@RequestParam("status") String status) {
		return commandeClientService.findListCommandeClientByStatus(status);
	}
	
	@GetMapping("/searchListCommandeClientByClientId")
	public List<CommandeClient> getAllCommandeClientByClientId(@RequestParam("clientId") Long clientId) {
		return commandeClientService.findCommandeClientByClientId(clientId);
	}
	
	@GetMapping("/searchListCommandeClientByPageable")
	public Page<CommandeClient> getCommandeClientByPageable(
			@RequestParam(name = "page") int page,
			@RequestParam(name = "size") int size) {
		return commandeClientService.findAllCommandeClientByPageable(PageRequest.of(page, size));
	}
		
	@GetMapping("/searchListCommandeClientByClientPageable")
	public Page<CommandeClient> getAllCommandeClientByPageable(@RequestParam(name = "prod")Long clientId,
			@RequestParam(name = "page") int page,
			@RequestParam(name = "size") int size) {
		return commandeClientService.findAllCommandeClientByClient(clientId, PageRequest.of(page, size));
	}
	
	
	@GetMapping("/searchListCommandeClientByKeyword")
	public Page<CommandeClient> getAllCommandeByPageable(@RequestParam(name = "mc")String mc,
			@RequestParam(name = "page") int page,
			@RequestParam(name = "size") int size) {
		return commandeClientService.findCommandeClientByKeyWord(mc, PageRequest.of(page, size));
	}

	/**
	 *
	 * @param commandeClient
	 * @return Methode qui marche ce 06/09/2020
	 */
	@PostMapping("/comms")
	public ResponseEntity<CommandeClient> enregistrerCommande(@RequestBody CommandeClient commandeClient) {
		commandeClientService.createCommande(commandeClient);
		
		List<LigneCmdClient> lcomms = commandeClient.getLigneCmdClients();
		for (LigneCmdClient lc : lcomms) {
			lc.setNumero(commandeClient.getNumCommande());
			ligneCmdClientService.saveLigneCmdClient(lc);
			
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
<<<<<<< HEAD
	@PostMapping("/comms")
	public ResponseEntity<CommandeClient> enregistrerCommande(@RequestBody CommandeClient commandeClient) {
		commandeClientRepository.save(commandeClient);
		
		List<LigneCmdClient> lcomms = commandeClient.getLigneCmdClients();
		for (LigneCmdClient lc : lcomms) {
			lc.setNumero(commandeClient.getNumCommande());
			ligneCmdClientRepository.save(lc);
			
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@PostMapping("/commandes") 
	public ResponseEntity<CommandeClient> createCommande(@RequestBody CommandeClient commandeClient) {
	    
		commandeClientService.createCommande(commandeClient);
=======
	@PostMapping("/commandes")
	public ResponseEntity<CommandeClient> createCommande(@RequestBody CommandeClient commandeClient) {
	    
		commandeClientService.createCommande(commandeClient);

//		List<LigneCmdClient> lcomms = commandeClient.getLigneCmdClients();
//		for (LigneCmdClient lc : lcomms) {
//			lc.setNumero(commandeClient.getNumCommande());
//			ligneCmdClientService.saveLigneCmdClient(lc);
//
//		}
>>>>>>> 962d992518874a2014c813f38e02d77021502842
		
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@PostMapping("/commandesClientes") 
<<<<<<< HEAD
	public CommandeClient createCommandeClient(@RequestBody CommandeClient commandeClient) {
		/*
		 * commandeClientService.saveCommandeCliente(commandeClient);
		 * List<LigneCmdClient> lcomms = commandeClient.getLigneCmdClients(); for
		 * (LigneCmdClient lc : lcomms) { lc.setNumero(commandeClient.getNumCommande());
		 * ligneCmdClientService.saveLigneCmdClient(lc); }
		 * commandeClient.setNumCommande("Cmd "+15+(int)(Math.random()*100));
		 * commandeClient.setStatus("valider"); commandeClient.setDateCommande(new
		 * Date());
		 */
	    
		return commandeClientService.saveCommandeCliente(commandeClient);
=======
	public CommandeClient createCommandeClient(@RequestBody CommandeClient commandeClient) throws Exception  {

		return commandeClientService.saveCommandeClient(commandeClient);
>>>>>>> 962d992518874a2014c813f38e02d77021502842
		
	}
	
	@PutMapping("/commandes/{id}")
	public ResponseEntity<CommandeClient>  updateLigneCmdClient(@PathVariable(value = "id") Long id, @RequestBody CommandeClient commandeClient) throws Exception {
		commandeClient.setId(id);
		return new ResponseEntity<>(commandeClientService.saveCommandeClient(commandeClient), HttpStatus.OK);
		
	}
	@DeleteMapping("/commandes/{id}")
	public ResponseEntity<Object> deleteCommandeClient(@PathVariable(value = "id") Long id) {
		return commandeClientService.deleteCommandeClient(id);
		
	}

}
