package com.library.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.library.entities.Client;

public interface ClientService {
	
	public List<Client> findAllClient();
	public Client saveClient(Client client);
	public Optional<Client> findClientById(Long id);
	public Client updateClient(Long id, Client client);
	public ResponseEntity<Object> deleteClient(Long id);
	
	public Client findByNom(String nom);
	public Client findByPrenom(String prenom);
	
	public List<Client> ListClientByNom(String nom);
	public List<Client> ListClientByPrneom(String prenom);
	
	public Page<Client> findAllClientByPage(Pageable page);
	public Page<Client> findClientByKeyWord(String mc, Pageable pageable);
	
	


}
