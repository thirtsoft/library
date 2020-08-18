package com.library.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.library.entities.Category;
import com.library.entities.Scategorie;
import com.library.repository.CategoryRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ScategorieRepositoryTest {
	
	@Autowired
	private ScategorieRepository scategorieRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	@Test
	@Rollback(false)
	public void testCreateScategorie() {
		
		Long catId = (long) 1;
		Optional<Category> category = categoryRepository.findById(catId);
		
		Scategorie scategorie = new Scategorie(null,"Bureau", "Chaise Roulante", category.get());
		
		Scategorie saveScategorie = scategorieRepository.save(scategorie);
		
		assertNotNull(saveScategorie);
	}
	
	@Test 
	public void testFindScategorieById() { 
		long scatId = 2;
		boolean isScategory = scategorieRepository.findById(scatId).isPresent();
	  
		assertTrue(isScategory);
	}
	
	@Test 
	public void testFindScategorieByCode() { 
		String code = "HP";
		Scategorie scategory = scategorieRepository.findByCode(code);
	  
		assertThat(scategory.getCode()).isEqualTo(code); 
	}
	

	@Test
	public void testFindScategorieByLibelle() {
		String libelle = "SCAT2";
		
		Scategorie scategory = scategorieRepository.findByLibelle(libelle);
		
		assertThat(scategory.getLibelle()).isEqualTo(libelle);
	}
	
	@Test 
	@Rollback(false)
	public void testUpdateScategorie() {
		Long catId = (long) 1;
		Optional<Category> category = categoryRepository.findById(catId);
		Category cat = category.get();
		
		String scategoryLibelle = "PAPIER RAM";
		Scategorie scat = new Scategorie(null, "Bureau", scategoryLibelle, cat);
		scat.setId((long) 2);
		scategorieRepository.save(scat);
		
		Scategorie scategoryUpdate = scategorieRepository.findByLibelle(scategoryLibelle);
		
		assertThat(scategoryUpdate.getLibelle()).isEqualTo(scategoryLibelle);
		
	}
	
	@Test
	public void testListScategories() {
		List<Scategorie> scategories = scategorieRepository.findAll();
		
		for (Scategorie scategory: scategories) {
			System.out.println(scategory);
		}
		assertThat(scategories).size().isGreaterThan(0);
	}
	
	@Test
	@Rollback(false)
	public void testDeleteScategorie() {
		Long id = (long) 7;
		
		boolean isExistBeforeDelete = scategorieRepository.findById(id).isPresent();
		
		scategorieRepository.deleteById(id);
		
		boolean notExistAfterDelete = scategorieRepository.findById(id).isPresent();
		
		assertTrue(isExistBeforeDelete);
		
		assertFalse(notExistAfterDelete);
	}
	
	@Test
	public void testListFindScategorieByCode() {
		String code = "U";
		
		List<Scategorie> scategories = scategorieRepository.findListScategorieByCode("%"+code+"%");
		List<Scategorie> scategoriesList = new ArrayList<Scategorie>();
		
		for (Scategorie scategory: scategories) {
			scategoriesList.add(scategory);
		}

		assertThat(scategoriesList.size()).isGreaterThan(0);
			
	}
	
	@Test
	public void testListFindScategorieByLibelle() {
		String libelle = "P";
		
		List<Scategorie> scategories = scategorieRepository.findListScategorieByLibelle("%"+libelle+"%");
		List<Scategorie> scategoriesList = new ArrayList<Scategorie>();
		
		for (Scategorie scategory: scategories) {
			scategoriesList.add(scategory);
		}
		assertThat(scategoriesList.size()).isEqualTo(2);
			
	}
	
	@Test 
	public void testFindListScategorieByCategory() { 
		Long catId = (long) 1;
		
		List<Scategorie> scategories = scategorieRepository.findScategorieByCateoryId(catId);
		List<Scategorie> scategorieList = new ArrayList<Scategorie>();
		
		for (Scategorie scategorie: scategories) {
			scategorieList.add(scategorie);
		}
		assertThat(scategorieList.size()).isEqualTo(2);
	}
	
}
