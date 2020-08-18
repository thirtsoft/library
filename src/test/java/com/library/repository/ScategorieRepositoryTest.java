package com.library.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.library.entities.Category;
import com.library.repository.CategoryRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ScategorieRepositoryTest {
	
	@Autowired
	private ScategorieRepository scategorieRepository;
	
	@Test
	@Rollback(false)
	public void testCreateCategory() {
		Category category = new Category(null,"Bureau", "Chaise Roulante");
		
		Category saveCategory = categoryRepository.save(category);
		
		assertNotNull(saveCategory);
	}
	
	@Test 
	public void testFindCategoryById() { 
		long catId = 2;
		boolean isCategory = categoryRepository.findById(catId).isPresent();
	  
		assertTrue(isCategory);
	}
	
	@Test 
	public void testFindCategoryByCode() { 
		String code = "CAT";
		Category category = categoryRepository.findByCode(code);
	  
		assertThat(category.getCode()).isEqualTo(code); 
	}
	
	@Test 
	public void testFindCategoryByCodeNotExist() { 
		String code = "CAT7";
		Category category = categoryRepository.findByCode(code);
	  
		assertNull(category); 
	}
	
	@Test
	public void testFindCategoryByDesignation() {
		String designation = "PROD1";
		Category category = categoryRepository.findByDesignation(designation);
		
		assertThat(category.getDesignation()).isEqualTo(designation);
	}
	
	@Test 
	@Rollback(false)
	public void TestUpdateCategory() {
		String categoryDesignation = "PAPIER RAM";
		Category cat = new Category(null, "Bureau", categoryDesignation);
		cat.setId((long) 16);
		categoryRepository.save(cat);
		
		Category categoryUpdate = categoryRepository.findByDesignation(categoryDesignation);
		
		assertThat(categoryUpdate.getDesignation()).isEqualTo(categoryDesignation);
		
	}
	
	@Test
	public void testListCategories() {
		List<Category> categories = categoryRepository.findAll();
		
		for (Category category: categories) {
			System.out.println(category);
		}
		assertThat(categories).size().isGreaterThan(0);
	}
	
	@Test
	@Rollback(false)
	public void testDeleteCategory() {
		Long id = (long) 7;
		
		boolean isExistBeforeDelete = categoryRepository.findById(id).isPresent();
		
		categoryRepository.deleteById(id);
		
		boolean notExistAfterDelete = categoryRepository.findById(id).isPresent();
		
		assertTrue(isExistBeforeDelete);
		
		assertFalse(notExistAfterDelete);
	}
	
	@Test
	public void testListFindCategoryByCode() {
		String code = "C";
		
		List<Category> categories = categoryRepository.ListCategoryByCode("%"+code+"%");
		List<Category> categoriesList = new ArrayList<Category>();
		
		for (Category category: categories) {
			categoriesList.add(category);
		}
		//assertThat(categoriesList.size()).isEqualTo(3);
		assertThat(categoriesList.size()).isGreaterThan(0);
			
	}
	
	@Test
	public void testListFindCategoryByDesignation() {
		String designation = "PROD";
		
		List<Category> categories = categoryRepository.ListCategoryByDesignation("%"+designation+"%");
		List<Category> categoriesList = new ArrayList<Category>();
		
		for (Category category: categories) {
			categoriesList.add(category);
		}
		assertThat(categoriesList.size()).isEqualTo(2);
		//assertThat(categoriesList.size()).isGreaterThan(0);
			
	}
	
}
