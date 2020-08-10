package com.library.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.library.entities.Produit;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.ProduitRepository;

public class ProduitServiceImpl implements ProduitService {
	
	@Autowired
	private ProduitRepository produitRepository;

	@Override
	public List<Produit> findAllProduits() {
		return produitRepository.findAll();
	}

	@Override
	public Optional<Produit> findProduitById(Long prodId) {
		if (!produitRepository.existsById(prodId)) {
			throw new ResourceNotFoundException("Produit that id is" + prodId + "is not found");
		}
		return produitRepository.findById(prodId);
	}

	@Override
	public Produit saveProduit(Long catId, Produit produit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Produit saveProduit(Produit produit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Produit updateProduit(Long prodId, Produit produit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Produit updateProduit(Produit produit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Object> deleteProduit(Long prodId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Produit findByReference(String reference) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Produit findByDesignation(String designation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Produit findByPrixAchat(Double prixAchat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Produit> searchProductByDesignation(String designation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Produit> findAllProduitsByPageable(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Produit> getProductByCateoryId(Long catId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Produit> findAllProduitsByCategory(Long catId, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Produit> findProduitByKeyWord(String mc, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
