package com.library.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.library.entities.Category;
import com.library.entities.Client;
import com.library.entities.CommandeClient;
import com.library.entities.Produit;

public interface CommandeClientService {

	public List<CommandeClient> findAllCommandeClient();
	public Optional<CommandeClient> findCommandeClientById(Long comId);
	public CommandeClient saveCommandeClient(CommandeClient commande);
	public CommandeClient updateCommandeClient(Long comId, CommandeClient commande);
	public ResponseEntity<Object> deleteCommandeClient(Long id);
	
	public CommandeClient saveCommandeClient(Long num, Date date, boolean valide, Client clt);
	
	public int getNombreCommandes(Date d1, Date d2);
	
	public CommandeClient findByNumCommande(String numCommande);
	public CommandeClient findByStatus(String status);
	
	public List<CommandeClient> findListCommandeClientByNumCommande(String numCommande);
	public List<CommandeClient> findListCommandeClientByStatus(String status);
	public List<CommandeClient> findCommandeClientByClientId(Long clientId);
	
	public Page<CommandeClient> findAllCommandeClientByPageable(Pageable pageable);
	public Page<CommandeClient> findAllCommandeClientByClient(Long clientId, Pageable pageable);
	
	public Page<CommandeClient> findCommandeClientByKeyWord(String mc, Pageable pageable);
	
	

}
