package com.library.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.library.entities.Category;
import com.library.entities.Produit;
import com.library.entities.Scategorie;
import com.library.repository.CategoryRepository;
import com.library.repository.ProduitRepository;
import com.library.repository.ScategorieRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProduitServiceTest {
	
	@Autowired
	private ProduitService produitService;
	
	@MockBean
	private ProduitRepository produitRepository;
	
	@MockBean
	private CategoryRepository categoryRepository;
	
	@MockBean
	private ScategorieRepository scategoryRepository;
	
	
	@Test
	public void testCreateProduit() {
		Category category = new Category(null,"PC","PCMobile");
		Scategorie scategory = new Scategorie(null,"DELL","DELL Compact", category);
		
		
		Produit produit = new Produit();
		produit.setReference("PROD1");
		produit.setDesignation("HP");
		produit.setPrixAchat((double) 2000);
	//	produit.setCategorie(category);
		produit.setScategorie(scategory);
		
		Mockito.when(produitRepository.save(produit)).thenReturn(produit);
		
		assertThat(produitService.saveProduit(produit)).isEqualTo(produit);
	}
	
	@Test
	public void testFindProduitByReference() {
		
		Category category = new Category(null,"Bureau", "Chaise Roulante");
		
		Scategorie scategory = new Scategorie(null,"Table", "Table Bois", category);
		
		Produit produit = new Produit(null,"Art3", "Ordi HP", 23000.0, 25000.0,26000.0, 20.0, 100,5, true, "photo", new Date(), scategory);
		
		when(produitRepository.findByReference(produit.getReference())).thenReturn(produit);
		
		Produit prod = produitService.findByReference(produit.getReference());
		
		assertNotNull(prod);
		assertThat(prod.getReference()).isEqualTo(produit.getReference());
		
	}
	
	@Test
	public void testFindProduitByDesignation() {
		
		Category category = new Category(null,"Bureau", "Chaise Roulante");
		
		Scategorie scategory = new Scategorie(null,"Bureau", "Chaise Roulante", category);
		
		Produit produit = new Produit(null,"Art3", "Ordi HP", 23000.0, 25000.0,26000.0,
				20.0, 500, 15, true, "photo", new Date(), scategory);
		
		when(produitRepository.findByDesignation(produit.getDesignation())).thenReturn(produit);
		
		Produit prod = produitService.findByDesignation(produit.getDesignation());
		
		assertNotNull(prod);
		assertThat(prod.getDesignation()).isEqualTo(produit.getDesignation());
		
	}
	
	
	@Test
	public void testAllProduits() {
		
		Category cat = new Category(null,"PC","PCMobile");
		
		Scategorie scat = new Scategorie(null,"PC","PCMobile", cat);
		
		when(produitRepository.findAll()).thenReturn(Stream
		.of(new Produit(null,"Art3", "Ordi HP", 23000.0, 25000.0,26000.0, 20.0, 200, 25, true, "photo", new Date(), scat),
						new Produit(null,"HPHP", "HPElioLite", 3000.0, 5000.0, 5500.0, 20.0, 500, 20, true, "photo", new Date(), scat)).collect(Collectors.toList()));
		assertEquals(2, produitService.findAllProduits().size());
	}

}
