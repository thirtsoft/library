package com.library.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.library.entities.Fournisseur;


public interface FournisseurService {
	
	public Optional<Fournisseur> findProduitById(Long id);
	public Fournisseur saveFournisseur(Fournisseur fournisseur);
	
	public Fournisseur updateFournisseurt(Long id, Fournisseur fournisseur);
	public ResponseEntity<Object> deleteFournisseur(Long id);
	
	public Fournisseur findByCode(String code);
	public Fournisseur findByNom(String nom);
	public Fournisseur findByRaisonSociale(String raisonSociale);
	
	public List<Fournisseur> findAllFournisseurs();
	public List<Fournisseur> findListFournisseurByCode(String code);
	public List<Fournisseur> findListFournisseurByNom(String nom);
	public List<Fournisseur> findListFournisseurByRaisonSociale(String raisonSociale);
	
	
	public Page<Fournisseur> findAllFournisseursByPageable(Pageable page);
	
	public Page<Fournisseur> findFournisseurByKeyWord(String mc, Pageable pageable);

}
