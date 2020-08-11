package com.library.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.library.entities.Category;
import com.library.entities.Produit;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.CategoryRepository;
import com.library.repository.ProduitRepository;

@Service
@Transactional
public class ProduitServiceImpl implements ProduitService {
	
	@Autowired
	private ProduitRepository produitRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

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
	public Produit findByReference(String reference) {
		return produitRepository.findByReference(reference);
	}

	@Override
	public Produit findByDesignation(String designation) {
		return produitRepository.findByDesignation(designation);
	}

	@Override
	public Produit findByPrixAchat(Double prixAchat) {
		return produitRepository.findByPrixAchat(prixAchat);
	}

	@Override
	public List<Produit> findListProduitByDesignation(String designation) {
		return produitRepository.findListProduitByDesignation(designation);
	}
		
	@Override
	public List<Produit> findProductByCateoryId(Long catId) {
		return produitRepository.findProductByCateoryId(catId);
	}
	
	@Override
	public Page<Produit> findAllProduitsByPageable(Pageable page) {
		return produitRepository.findAllProduitsByPageable(page);
	}

	@Override
	public Page<Produit> findAllProduitsByCategory(Long catId, Pageable pageable) {
		return produitRepository.findProduitByCateoryId(catId, pageable);
	}

	@Override
	public Page<Produit> findProduitByKeyWord(String mc, Pageable pageable) {
		return produitRepository.findProduitByKeyWord(mc, pageable);
	}

	@Override
	public Produit saveProduit(Long catId, Produit produit) {
		Set<Produit> products = new HashSet<Produit>(); 
		
		Category cat = new Category();
	  
		Optional<Category> catbyId = categoryRepository.findById(catId);
		
		if(!catbyId.isPresent()) { throw new ResourceNotFoundException("Category" +catId + "not found"); 
		
		} 
		
		Category category = catbyId.get();
		
		Produit prod = produitRepository.save(produit); products.add(prod);
		//cat.setProducts(products);
	  
		return prod;
	}

	@Override
	public Produit saveProduit(Produit produit) {
		return produitRepository.save(produit);
	}

	@Override
	public Produit updateProduit(Long prodId, Produit produit) {
		if(!produitRepository.existsById(prodId)) {
			throw new ResourceNotFoundException("Produit that id is" + prodId + "not found");
		}
		Optional<Produit> prod = produitRepository.findById(prodId);
		
		if(!prod.isPresent()) {
			throw new ResourceNotFoundException("Produit that id is" + prodId + "not found");
			
		}
		Produit product = prod.get();
		
		product.setReference(produit.getReference());
		product.setDesignation(produit.getDesignation());
		product.setPrixAchat(produit.getPrixAchat());
		product.setPrixVente(produit.getPrixVente());
		product.setStockInitial(produit.getStockInitial());
		product.setTva(produit.getTva());
		product.setPromo(produit.isPromo());
		product.setAdd_date(produit.getAdd_date());
		product.setPhoto(produit.getPhoto());
		//product1.setCategorie(product.getCategorie());
		
		return produitRepository.save(product);
	}

	@Override
	public Produit updateProduit(Produit produit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Object> deleteProduit(Long prodId) {
		if(!produitRepository.existsById(prodId)) {
			throw new ResourceNotFoundException("Produit that id is" + prodId + "not found");
		}
		
		produitRepository.deleteById(prodId);
		
		return ResponseEntity.ok().build();
	}

	
}
