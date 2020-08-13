package com.library.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.library.entities.Category;
import com.library.entities.Produit;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProduitRepositoryTest {
	
	@Autowired
	private ProduitRepository produitRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	@Test
	@Rollback(false)
	public void testCreateProduit() {
		Long catId = (long) 6;
		Optional<Category> category = categoryRepository.findById(catId);
		Produit produit = new Produit(null,"Art3", "Ordi HP", 23000.0, 25000.0, 20.0, 5, true, "photo", new Date(), category.get());
		
		Produit saveProduit = produitRepository.save(produit);
		
		assertNotNull(saveProduit);
	}
	
	@Test 
	public void testFindProduitById() { 
		long prodId = 3;
		boolean isProduitExist = produitRepository.findById(prodId).isPresent();
	  
		assertTrue(isProduitExist);
	}
	
	@Test 
	public void testFindProduitByReference() { 
		String reference = "PAP1";
		Produit produit = produitRepository.findByReference(reference);
	  
		assertThat(produit.getReference()).isEqualTo(reference); 
	}
	
	@Test 
	public void testFindListProduitByDesignation() { 
		String designation = "PAP";
		List<Produit> produits = produitRepository.findListProduitByDesignation("%"+designation+"%");
		List<Produit> produitList = new ArrayList<Produit>();
		
		for (Produit produit: produits) {
			produitList.add(produit);
		}
		//assertThat(categoriesList.size()).isEqualTo(3);
		assertThat(produitList.size()).isGreaterThan(0);
	}
	
	@Test 
	public void testFindListProduitByCategory() { 
		Long catId = (long) 6;
		
		List<Produit> produits = produitRepository.findProductByCateoryId(catId);
		List<Produit> produitList = new ArrayList<Produit>();
		
		for (Produit produit: produits) {
			produitList.add(produit);
		}
		//assertThat(categoriesList.size()).isEqualTo(3);
		assertThat(produitList.size()).isGreaterThan(0);
	}
	
	@Test 
	@Rollback(false)
	public void TestUpdateProduit() {
		String prodDesignation = "PAPIER DE RAM";
		String prodReference = "RAM2";
		Double prodprixAchat = 5000.0;
		Double prodprixVente = 10000.0;
		Double prodtva = 5.0;
		
		Long catId = (long) 6;
		Optional<Category> category = categoryRepository.findById(catId);
		Category cat = category.get();
		
		Produit produit = new Produit(null,prodReference, prodDesignation, prodprixAchat, 
				prodprixVente, prodtva, 15, true, "photoUpdate", new Date(), cat);
		
		produit.setId((long) 7);
		produitRepository.save(produit);
		
		Produit produitUpdate = produitRepository.findByReference(prodReference);
		
		assertThat(produitUpdate.getReference()).isEqualTo(prodReference);
		
	}
	
	@Test
	@Rollback(false)
	public void testDeleteProduit() {
		Long id = (long) 13;
		
		boolean isExistBeforeDelete = produitRepository.findById(id).isPresent();
		
		produitRepository.deleteById(id);
		
		boolean notExistAfterDelete = produitRepository.findById(id).isPresent();
		
		assertTrue(isExistBeforeDelete);
		
		assertFalse(notExistAfterDelete);
	}


}
