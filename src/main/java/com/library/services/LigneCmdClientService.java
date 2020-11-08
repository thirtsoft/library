package com.library.services;

import java.util.List;
import java.util.Optional;

import com.library.entities.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.library.entities.LigneCmdClient;

public interface LigneCmdClientService {
	
	List<LigneCmdClient> findAllLigneCmdClient();
	Optional<LigneCmdClient> findLigneCmdClientById(Long lCmdId);
	LigneCmdClient saveLigneCmdClient(LigneCmdClient ligneCmdClient);
	LigneCmdClient updateLigneCmdClient(Long lCmdId, LigneCmdClient ligneCmdClient);
	ResponseEntity<Object> deleteLigneCmdClient(Long id);

	void deleteLcomByNumero(int numero);
	//public LigneCmdClient findByQuantite(int quantite);
	
	//public List<LigneCmdClient> findListLigneCmdClientByQuantite(int quantite);
	public List<LigneCmdClient> findLigneCmdClientByProduitId(Long prodId);
	public List<LigneCmdClient> findLigneCmdClientByCommandeClientId(Long comId);
	List<LigneCmdClient> findAllLcomByNumero(int numero);
	
	public Page<LigneCmdClient> findAllLigneCmdClientByPageable(Pageable pageable);
	public Page<LigneCmdClient> findAllLigneCmdClientByCommandeClient(Long comId, Pageable pageable);
	public Page<LigneCmdClient> findAllLigneCmdClientByProduit(Long prodId, Pageable pageable);
	
	//public Page<LigneCmdClient> findLigneCmdClientByKeyWord(String mc, Pageable pageable);

	boolean isValideQuantiteStock(int qtesSaisie, int qtitesStock);

	public List<LigneCmdClient> saveListLigneCmd(List<LigneCmdClient> lcmdClients);
}
