package com.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.library.entities.Client;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.ClientService;

@RestController
@CrossOrigin
@RequestMapping("/alAmine")
public class ClientController {
	
	@Autowired
	private ClientService clientService;
	
	@GetMapping("/clients")
	public List<Client> getAllClients() {
		return clientService.findAllClient();
		
	}
	
	@GetMapping("/clients/{id}")
	public ResponseEntity<Client> getClientById(@PathVariable(value = "id") Long id)
			throws ResourceNotFoundException{
		Client client = clientService.findClientById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Client that id is" + id + "not found"));
		return ResponseEntity.ok().body(client);
		
	}

	@GetMapping("/NumberOfClients")
	public Long getNumberOfClient() {
		return clientService.countNumberOfClient();
	}

	@GetMapping("/searchClientByEmail")
	public Client getClientByEmail(String email) {
		return clientService.findByEmail(email);
	}
	
	@GetMapping("/searchClientByRaisonSocial")
	public Client getClientByRaisonSocial(@RequestParam(value = "raisonSocial") String raisonSocial) {
		return clientService.findByRaisonSocial(raisonSocial);
		
	}
	
	@GetMapping("/searchListClientByRaisonSocial")
	public List<Client> getListClientByRaisonSocial(@RequestParam(value = "raisonSocial") String raisonSocial) {
		
		return clientService.ListClientByRaisonSocial("%"+raisonSocial+"%");
		
	}

	@GetMapping("/ListClientGroupByRaisonSocial")
	public List<Object[]> getListClientGroupByRaisonSocial() {
		return clientService.ListClientGroupByRaisonSocial();
	}
	
	@GetMapping("/searchClientByChefService")
	public Client getClientByChefService(@RequestParam(value = "chefService") String chefService) {
		return clientService.findByChefService(chefService);
	}
	
	@GetMapping("/searchListClientByChefService")
	public List<Client> getListClientByChefService(@RequestParam(value = "chefService") String chefService) {
		return clientService.ListClientByChefService("%"+chefService+"%");
	}

	@GetMapping("/searchListClientByPageable")
	public Page<Client> getAllClientByPageable(@RequestParam(name = "page") int page,
			@RequestParam(name = "size") int size) {
		return clientService.findAllClientByPage(PageRequest.of(page, size));
	}
	
	@GetMapping(value = "/searchListClientByKeyword")
	public Page<Client> findClientByKeyWord(@RequestParam(name="mc", defaultValue="") String mc, 
			@RequestParam(name = "page") int page,
			@RequestParam(name = "size") int size) {
		return clientService.findClientByKeyWord("%"+mc+"%", PageRequest.of(page, size));
	}

	@PostMapping("/clients") 
	public ResponseEntity<Client> createClient(@RequestBody Client client) {
		if (clientService.findByCodeClient(client.getCodeClient()) != null) {
			return new ResponseEntity<Client>(client, HttpStatus.BAD_REQUEST);
		}
		if (clientService.findByEmail(client.getEmail()) != null) {
			return new ResponseEntity<Client>(client, HttpStatus.BAD_REQUEST);
		}
		if (clientService.findByTelephone(client.getTelephone()) != null) {
			return new ResponseEntity<Client>(client, HttpStatus.BAD_REQUEST);
		}
		clientService.saveClient(client);
		return new ResponseEntity<Client>(client, HttpStatus.CREATED);
	}
	
	@PutMapping("/clients/{clientId}")
	public ResponseEntity<Client>  updateClient(@PathVariable(value = "clientId") Long clientId, @RequestBody Client client) {
		client.setId(clientId);
		return new ResponseEntity<>(clientService.saveClient(client), HttpStatus.OK);
		
	}

	@PatchMapping("/udapteClientByEmail/{id}")
	public ResponseEntity<?> updateClientByEmail(@RequestParam("email") String email, @PathVariable("id") String id) {
		Client newClient = clientService.updateClientByEmail(email, id);
		return new ResponseEntity<>(newClient, HttpStatus.OK);
	}

	@DeleteMapping("/clients/{id}")
	public ResponseEntity<?> deleteClient(@PathVariable(value = "id") Long id) {
		clientService.deleteClient(id);
		return ResponseEntity.ok().build();
		
	}

}
