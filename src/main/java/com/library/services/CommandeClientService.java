package com.library.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.boot.json.JsonParseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.library.entities.Client;
import com.library.entities.CommandeClient;

public interface CommandeClientService {

	public List<CommandeClient> findAllCommandeClient();
	public Optional<CommandeClient> findCommandeClientById(Long comId);
	public CommandeClient saveCommandeClient(CommandeClient commande);
	
	public CommandeClient updateCommandeClient(Long comId, CommandeClient commande);
	public ResponseEntity<Object> deleteCommandeClient(Long id);
	
	//public CommandeClient saveCommandeClient(Long num, Date date, boolean valide, Client clt);

	public int getNombreCommandes(Date d1, Date d2);
	int getNumberOfCommande();
	
	public CommandeClient findByNumeroCommande(int numeroCommande);
	public CommandeClient findByStatus(String status);

	public List<CommandeClient> findListCommandeClientByStatus(String status);
	public List<CommandeClient> findCommandeClientByClientId(Long clientId);

	public List<CommandeClient> findCommandeByDate(Date dateCommande);
	
	public Page<CommandeClient> findAllCommandeClientByPageable(Pageable pageable);
	public Page<CommandeClient> findAllCommandeClientByClient(Long clientId, Pageable pageable);
	
	//public Page<CommandeClient> findCommandeClientByKeyWord(int mc, Pageable pageable);
	
	//public CommandeClient saveCommandeCliente(CommandeClient commande);
	
	public CommandeClient createCommande(CommandeClient commandeClient);


    ResponseEntity<String> createOrder(CommandeClient commandeClient);

    void deleteCommande(Long id);
	

}
