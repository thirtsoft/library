package com.library.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.library.entities.LigneCmdClient;

public interface LigneCmdClientService {
	
	public List<LigneCmdClient> findAllLigneCmdClient();
	public Optional<LigneCmdClient> findLigneCmdClientById(Long lCmdId);
	public LigneCmdClient saveLigneCmdClient(LigneCmdClient ligneCmdClient);
	public LigneCmdClient updateLigneCmdClient(Long lCmdId, LigneCmdClient ligneCmdClient);
	public ResponseEntity<Object> deleteLigneCmdClient(Long id);
	
	//public LigneCmdClient findByQuantite(int quantite);
	
	//public List<LigneCmdClient> findListLigneCmdClientByQuantite(int quantite);
	public List<LigneCmdClient> findLigneCmdClientByProduitId(Long prodId);
	public List<LigneCmdClient> findLigneCmdClientByCommandeClientId(Long comId);
	
	public Page<LigneCmdClient> findAllLigneCmdClientByPageable(Pageable pageable);
	public Page<LigneCmdClient> findAllLigneCmdClientByCommandeClient(Long comId, Pageable pageable);
	public Page<LigneCmdClient> findAllLigneCmdClientByProduit(Long prodId, Pageable pageable);
	
	//public Page<LigneCmdClient> findLigneCmdClientByKeyWord(String mc, Pageable pageable);

}
