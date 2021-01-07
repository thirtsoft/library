package com.library.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.library.entities.Client;

public interface ClientService {
	
	List<Client> findAllClient();
	Client saveClient(Client client);
	Optional<Client> findClientById(Long id);
	Client updateClient(Long id, Client client);
	ResponseEntity<Object> deleteClient(Long id);
	
	Client findByRaisonSocial(String raisonSocial);
	Client findByChefService(String chefService);
	Client findByEmail(String email);
	
	List<Client> ListClientByRaisonSocial(String raisonSocial);
	List<Client> ListClientByChefService(String chefService);
	List<Object[]> ListClientGroupByRaisonSocial();
	
	Page<Client> findAllClientByPage(Pageable page);
	Page<Client> findClientByKeyWord(String mc, Pageable pageable);

	Long countNumberOfClient();

	Client updateClientByEmail(String email, String id);
	


}
