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
	
	@GetMapping("/searchClientByRaisonSocial")
	public Client getClientByRaisonSocial(@RequestParam(value = "raisonSocial") String raisonSocial) {
		return clientService.findByRaisonSocial(raisonSocial);
		
	}
	
	@GetMapping("/searchListClientByRaisonSocial")
	public List<Client> getListClientByRaisonSocial(@RequestParam(value = "raisonSocial") String raisonSocial) {
		
		return clientService.ListClientByRaisonSocial("%"+raisonSocial+"%");
		
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
	public Client createClient(@RequestBody Client client) {
		return clientService.saveClient(client);	
	}
	
	@PutMapping("/clients/{clientId}")
	public ResponseEntity<Client>  updateClient(@PathVariable(value = "clientId") Long clientId, @RequestBody Client client) {
		client.setId(clientId);
		return new ResponseEntity<>(clientService.saveClient(client), HttpStatus.OK);
		
	}
	@DeleteMapping("/clients/{id}")
	public ResponseEntity<Object> deleteClient(@PathVariable(value = "id") Long id) {
		return clientService.deleteClient(id);
		
	}

}
