package com.library.repository;

import com.library.entities.Produit;
import com.library.entities.Scategorie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProduitRepositoryTest {
	
	@Autowired
	private ProduitRepository produitRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ScategorieRepository scategoryRepository;
	
	
	
	@Test
	@Rollback(false)
	public void testCreateProduit() {
		/*
		Long catId = (long) 6;
		Optional<Category> category = categoryRepository.findById(catId);
		*/

		Long scatId = (long) 2;
		Optional<Scategorie> scat = scategoryRepository.findById(scatId);
		
		Produit produit = new Produit(null,"Art3", "Ordi HP", 23000.0,25000.0,26000.0, 20, 10, scat.get());
		
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
/*
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
*/
	@Test 
	@Rollback(false)
	public void TestUpdateProduit() {
		String prodDesignation = "PAPIER DE RAM";
		String prodReference = "RAM2";
		Double prodprixAchat = 5000.0;
		Double prodprixVente = 10000.0;
		Double prixDetail = 11000.0;
		/*
		Long catId = (long) 6;
		Optional<Category> category = categoryRepository.findById(catId);
		Category cat = category.get();
		*/
		Long scatId = (long) 6;
		Optional<Scategorie> scategory = scategoryRepository.findById(scatId);
		Scategorie scat = scategory.get();
		
		Produit produit = new Produit(null,prodReference, prodDesignation, prodprixAchat, 
				prodprixVente,prixDetail, 300, 15, scat);
		
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
