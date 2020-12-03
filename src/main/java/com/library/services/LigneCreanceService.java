package com.library.services;

import com.library.entities.LigneCmdClient;
import com.library.entities.LigneCreance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface LigneCreanceService {
	
	List<LigneCreance> findAllLigneCreances();
	Optional<LigneCreance> findLigneCreanceById(Long lCreanceId);
	LigneCreance saveLigneCreance(LigneCreance ligneCreance);
	LigneCreance updateLigneCreance(Long lCreanceId, LigneCreance ligneCreance);
	ResponseEntity<Object> deleteLigneCreance(Long creanceId);
	void deleteLcreanceByNumero(int numero);


	List<LigneCreance> findLigneCreanceByProduitId(Long prodId);
	List<LigneCreance> findLigneCreanceByCreanceId(Long creanceId);
	List<LigneCreance> findAllLcreanceByNumero(int numero);


}
