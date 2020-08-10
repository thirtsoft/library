package com.library.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import com.library.entities.Produit;

public interface ProduitService {
	public List<Produit> findAllProduits();
	public Optional<Produit> findProduitById(Long prodId);
	public Produit saveProduit(Long catId, Produit produit);
	public Produit saveProduit(Produit produit);
	
	public Produit updateProduit(Long prodId, Produit produit);
	public Produit updateProduit(Produit produit);
	public ResponseEntity<Object> deleteProduit(Long prodId);
	
	public Produit findByReference(String reference);
	public Produit findByDesignation(String designation);
	public Produit findByPrixAchat(Double prixAchat);
	
	
	public List<Produit> searchProductByDesignation(String designation);
	

	Page<Produit> findAllProduitsByPageable(Pageable page);
	public List<Produit> getProductByCateoryId(@Param("cat") Long catId);
	Page<Produit>findAllProduitsByCategory(Long catId, Pageable pageable);
	
	Page<Produit> findProduitByKeyWord(String mc, Pageable pageable);
	


}
