package com.library.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.library.entities.Fournisseur;


public interface FournisseurService {
	
	Optional<Fournisseur> findProduitById(Long id);
	Fournisseur saveFournisseur(Fournisseur fournisseur);

	Fournisseur updateFournisseurt(Long id, Fournisseur fournisseur);
	ResponseEntity<Object> deleteFournisseur(Long id);
	
	Fournisseur findByCode(String code);
	Fournisseur findByNom(String nom);
	Fournisseur findByRaisonSociale(String raisonSociale);
	Fournisseur findByEmail(String email);
	
	List<Fournisseur> findAllFournisseurs();
	List<Fournisseur> findListFournisseurByCode(String code);
	List<Fournisseur> findListFournisseurByNom(String nom);
	List<Fournisseur> findListFournisseurByRaisonSociale(String raisonSociale);
	
	
	public Page<Fournisseur> findAllFournisseursByPageable(Pageable page);
	
	public Page<Fournisseur> findFournisseurByKeyWord(String mc, Pageable pageable);

}
